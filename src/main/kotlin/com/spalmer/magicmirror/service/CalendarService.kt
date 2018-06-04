package com.spalmer.magicmirror.service

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport

import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.spalmer.magicmirror.model.CalendarDTO
import org.springframework.stereotype.Component


import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class CalendarService {

  @Throws(IOException::class, GeneralSecurityException::class)
  fun getCalendars(): List<CalendarDTO> {
    // Build a new authorized API client service.
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
    Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).build()
    val service = Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
      .setApplicationName(APPLICATION_NAME)
      .build()

    // List the next 10 events from the primary calendar.
    val now = DateTime(System.currentTimeMillis())
    val events = service.events().list("primary")
      .setMaxResults(10)
      .setTimeMin(now)
      .setOrderBy("startTime")
      .setSingleEvents(true)
      .execute()
    val items = events.items
    return items.map(this::itemToCalendar)
  }

  private fun itemToCalendar(event: Event): CalendarDTO {
    val dateTime = event.start.dateTime
    val calendarTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTime.value / 1000), TimeZone.getDefault().toZoneId())
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    return CalendarDTO(event.summary, formatter.format(calendarTime));
  }

  companion object {
    private val APPLICATION_NAME = "Magic Mirror"
    private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    private val CREDENTIALS_FOLDER = "/opt/google"


    private val SCOPES = listOf(CalendarScopes.CALENDAR_READONLY)
    private val CLIENT_SECRET_DIR = "client_secret.json"

    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential {

      val inStream = this::class.java.classLoader.getResource(CLIENT_SECRET_DIR).openStream()
      val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(inStream))

      val flow = GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(FileDataStoreFactory(java.io.File(CREDENTIALS_FOLDER)))
        .setAccessType("offline")
        .build()
      return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
    }
  }
}
