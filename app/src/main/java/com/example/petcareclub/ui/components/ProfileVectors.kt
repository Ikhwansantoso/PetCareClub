package com.example.petcareclub.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val PawIcon: ImageVector
    get() = ImageVector.Builder(
        name = "PawIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color(0xFF5F69C9))) {
            moveTo(12f, 10f)
            curveTo(9.5f, 10f, 8f, 12f, 8f, 15f)
            curveTo(8f, 18.5f, 10f, 20f, 12f, 20f)
            curveTo(14f, 20f, 16f, 18.5f, 16f, 15f)
            curveTo(16f, 12f, 14.5f, 10f, 12f, 10f)
            close()
        }
        path(fill = SolidColor(Color(0xFF5F69C9))) {
            moveTo(5.5f, 11f)
            curveTo(4.7f, 11f, 4f, 12f, 4f, 13f)
            curveTo(4f, 14f, 4.7f, 15f, 5.5f, 15f)
            curveTo(6.3f, 15f, 7f, 14f, 7f, 13f)
            curveTo(7f, 12f, 6.3f, 11f, 5.5f, 11f)
            close()
        }
        path(fill = SolidColor(Color(0xFF5F69C9))) {
            moveTo(18.5f, 11f)
            curveTo(17.7f, 11f, 17f, 12f, 17f, 13f)
            curveTo(17f, 14f, 17.7f, 15f, 18.5f, 15f)
            curveTo(19.3f, 15f, 20f, 14f, 20f, 13f)
            curveTo(20f, 12f, 19.3f, 11f, 18.5f, 11f)
            close()
        }
        path(fill = SolidColor(Color(0xFF5F69C9))) {
            moveTo(8.5f, 6f)
            curveTo(7.7f, 6f, 7f, 7f, 7f, 8f)
            curveTo(7f, 9f, 7.7f, 10f, 8.5f, 10f)
            curveTo(9.3f, 10f, 10f, 9f, 10f, 8f)
            curveTo(10f, 7f, 9.3f, 6f, 8.5f, 6f)
            close()
        }
        path(fill = SolidColor(Color(0xFF5F69C9))) {
            moveTo(15.5f, 6f)
            curveTo(14.7f, 6f, 14f, 7f, 14f, 8f)
            curveTo(14f, 9f, 14.7f, 10f, 15.5f, 10f)
            curveTo(16.3f, 10f, 17f, 9f, 17f, 8f)
            curveTo(17f, 7f, 16.3f, 6f, 15.5f, 6f)
            close()
        }
    }.build()

val ClipboardIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ClipboardIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(19f, 5f)
            lineTo(15f, 5f)
            curveTo(15f, 3.5f, 13.5f, 2f, 12f, 2f)
            curveTo(10.5f, 2f, 9f, 3.5f, 9f, 5f)
            lineTo(5f, 5f)
            curveTo(3.9f, 5f, 3f, 5.9f, 3f, 7f)
            lineTo(3f, 20f)
            curveTo(3f, 21.1f, 3.9f, 22f, 5f, 22f)
            lineTo(19f, 22f)
            curveTo(20.1f, 22f, 21f, 21.1f, 21f, 20f)
            lineTo(21f, 7f)
            curveTo(21f, 5.9f, 20.1f, 5f, 19f, 5f)
            close()
        }
        path(fill = SolidColor(Color(0xFF5F69C9))) {
            moveTo(9f, 4f)
            lineTo(15f, 4f)
            lineTo(14f, 6f)
            lineTo(10f, 6f)
            close()
        }
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(7f, 10f); lineTo(17f, 10f)
            moveTo(7f, 14f); lineTo(14f, 14f)
            moveTo(7f, 18f); lineTo(17f, 18f)
        }
    }.build()

val InfoIcon: ImageVector
    get() = ImageVector.Builder(
        name = "InfoIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()
        }
        path(fill = SolidColor(Color(0xFF5F69C9))) {
            moveTo(12f, 7f)
            curveTo(12.55f, 7f, 13f, 6.55f, 13f, 6f)
            curveTo(13f, 5.45f, 12.55f, 5f, 12f, 5f)
            curveTo(11.45f, 5f, 11f, 5.45f, 11f, 6f)
            curveTo(11f, 6.55f, 11.45f, 7f, 12f, 7f)
            close()
        }
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(12f, 10f)
            lineTo(12f, 17f)
        }
    }.build()

val ChevronRightIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ChevronRightIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(stroke = SolidColor(Color(0xFF6A729A)), strokeLineWidth = 2f) {
            moveTo(9f, 6f)
            lineTo(15f, 12f)
            lineTo(9f, 18f)
        }
    }.build()

val GiftIconProfile: ImageVector
    get() = ImageVector.Builder(
        name = "GiftIconProfile",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(5f, 10f); lineTo(19f, 10f); lineTo(19f, 21f); lineTo(5f, 21f); close()
        }
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(3f, 7f); lineTo(21f, 7f); lineTo(21f, 11f); lineTo(3f, 11f); close()
        }
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(12f, 7f); lineTo(12f, 21f)
        }
        path(stroke = SolidColor(Color(0xFF5F69C9)), strokeLineWidth = 2f) {
            moveTo(12f, 7f)
            curveTo(10f, 3f, 6f, 3f, 12f, 7f)
            curveTo(14f, 3f, 18f, 3f, 12f, 7f)
        }
    }.build()

val LogoutIcon: ImageVector
    get() = ImageVector.Builder(
        name = "LogoutIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(stroke = SolidColor(Color(0xFFFF8A80)), strokeLineWidth = 2f) {
            moveTo(9f, 21f)
            lineTo(4f, 21f)
            lineTo(4f, 3f)
            lineTo(9f, 3f)
        }
        path(stroke = SolidColor(Color(0xFFFF8A80)), strokeLineWidth = 2f) {
            moveTo(12f, 12f)
            lineTo(20f, 12f)
            moveTo(17f, 9f)
            lineTo(20f, 12f)
            lineTo(17f, 15f)
        }
    }.build()
