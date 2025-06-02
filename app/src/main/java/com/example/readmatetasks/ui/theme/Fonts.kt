package com.example.readmatetasks.ui.theme

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont.Provider
import androidx.compose.ui.text.font.FontFamily
import com.example.readmatetasks.R

// Google Fonts Provider
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Fuente Koh Santepheap
val kohFont = GoogleFont("Koh Santepheap")

val KohFontFamily = FontFamily(
    Font(googleFont = kohFont, fontProvider = provider)
)