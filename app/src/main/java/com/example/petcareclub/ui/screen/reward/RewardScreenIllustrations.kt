package com.example.petcareclub.ui.screen.reward

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform

@Composable
fun VectorGiftBox(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        
        // Shadow at the bottom
        drawOval(
            color = Color.Black.copy(alpha = 0.15f),
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.1f, h * 0.82f),
            size = androidx.compose.ui.geometry.Size(w * 0.8f, h * 0.12f)
        )

        // Box Body (lower part)
        val bodyGradient = Brush.verticalGradient(
            colors = listOf(Color(0xFF7E8BF5), Color(0xFF4E5BC2))
        )
        drawRoundRect(
            brush = bodyGradient,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.38f),
            size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.45f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.08f)
        )

        // Lid (upper part)
        val lidGradient = Brush.verticalGradient(
            colors = listOf(Color(0xFF9FA8FA), Color(0xFF5F69C9))
        )
        drawRoundRect(
            brush = lidGradient,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.15f, h * 0.28f),
            size = androidx.compose.ui.geometry.Size(w * 0.7f, h * 0.15f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.04f)
        )

        // Ribbons
        val ribbonBrush = Brush.verticalGradient(
            colors = listOf(Color(0xFFFFD54F), Color(0xFFFFB300))
        )
        
        // Vertical Ribbon
        drawRect(
            brush = ribbonBrush,
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.45f, h * 0.28f),
            size = androidx.compose.ui.geometry.Size(w * 0.1f, h * 0.55f)
        )

        // Bow Loops
        val bowPathLeft = Path().apply {
            moveTo(w * 0.5f, h * 0.28f)
            cubicTo(w * 0.2f, h * 0.05f, w * 0.35f, h * 0.25f, w * 0.5f, h * 0.28f)
            close()
        }
        val bowPathRight = Path().apply {
            moveTo(w * 0.5f, h * 0.28f)
            cubicTo(w * 0.8f, h * 0.05f, w * 0.65f, h * 0.25f, w * 0.5f, h * 0.28f)
            close()
        }
        drawPath(
            path = bowPathLeft,
            brush = ribbonBrush
        )
        drawPath(
            path = bowPathRight,
            brush = ribbonBrush
        )

        // Bow Knot
        drawCircle(
            brush = ribbonBrush,
            radius = w * 0.07f,
            center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.28f)
        )
    }
}

@Composable
fun VectorVaccine(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2
        val cy = h / 2
        
        withTransform({
            rotate(-45f, pivot = androidx.compose.ui.geometry.Offset(cx, cy))
        }) {
            // Liquid inside barrel
            val liquidBrush = Brush.verticalGradient(
                colors = listOf(Color(0xFF80DEEA), Color(0xFF00ACC1))
            )
            drawRoundRect(
                brush = liquidBrush,
                topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.08f, cy - h * 0.1f),
                size = androidx.compose.ui.geometry.Size(w * 0.16f, h * 0.3f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.02f)
            )

            // Stopper
            drawRect(
                color = Color(0xFF37474F),
                topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.08f, cy - h * 0.14f),
                size = androidx.compose.ui.geometry.Size(w * 0.16f, h * 0.04f)
            )

            // Plunger Rod
            drawLine(
                color = Color(0xFF90A4AE),
                start = androidx.compose.ui.geometry.Offset(cx, cy - h * 0.14f),
                end = androidx.compose.ui.geometry.Offset(cx, cy - h * 0.35f),
                strokeWidth = w * 0.03f
            )

            // Plunger Top Handle
            drawLine(
                color = Color(0xFF5F69C9),
                start = androidx.compose.ui.geometry.Offset(cx - w * 0.1f, cy - h * 0.35f),
                end = androidx.compose.ui.geometry.Offset(cx + w * 0.1f, cy - h * 0.35f),
                strokeWidth = w * 0.04f
            )

            // Barrel outline
            drawRoundRect(
                color = Color(0xFF5F69C9),
                topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.1f, cy - h * 0.2f),
                size = androidx.compose.ui.geometry.Size(w * 0.2f, h * 0.45f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.04f),
                style = Stroke(width = w * 0.04f)
            )

            // Barrel ears
            drawLine(
                color = Color(0xFF5F69C9),
                start = androidx.compose.ui.geometry.Offset(cx - w * 0.16f, cy - h * 0.2f),
                end = androidx.compose.ui.geometry.Offset(cx + w * 0.16f, cy - h * 0.2f),
                strokeWidth = w * 0.04f
            )

            // Needle Hub
            drawRect(
                color = Color(0xFFB0BEC5),
                topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.04f, cy + h * 0.25f),
                size = androidx.compose.ui.geometry.Size(w * 0.08f, h * 0.05f)
            )

            // Needle
            drawLine(
                color = Color(0xFF78909C),
                start = androidx.compose.ui.geometry.Offset(cx, cy + h * 0.3f),
                end = androidx.compose.ui.geometry.Offset(cx, cy + h * 0.42f),
                strokeWidth = w * 0.015f
            )
            
            // Droplet
            drawCircle(
                color = Color(0xFF00ACC1),
                radius = w * 0.025f,
                center = androidx.compose.ui.geometry.Offset(cx - w * 0.04f, cy + h * 0.46f)
            )
        }
    }
}

@Composable
fun VectorGrooming(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2
        val cy = h / 2

        // Finger loop 1
        drawCircle(
            color = Color(0xFF5F69C9),
            radius = w * 0.12f,
            center = androidx.compose.ui.geometry.Offset(cx - w * 0.18f, cy - h * 0.2f),
            style = Stroke(width = w * 0.05f)
        )
        
        // Finger loop 2
        drawCircle(
            color = Color(0xFF5F69C9),
            radius = w * 0.12f,
            center = androidx.compose.ui.geometry.Offset(cx + w * 0.18f, cy - h * 0.2f),
            style = Stroke(width = w * 0.05f)
        )

        // Shorter connections to pivot
        drawLine(
            color = Color(0xFF5F69C9),
            start = androidx.compose.ui.geometry.Offset(cx - w * 0.12f, cy - h * 0.12f),
            end = androidx.compose.ui.geometry.Offset(cx - w * 0.03f, cy - h * 0.02f),
            strokeWidth = w * 0.05f
        )
        drawLine(
            color = Color(0xFF5F69C9),
            start = androidx.compose.ui.geometry.Offset(cx + w * 0.12f, cy - h * 0.12f),
            end = androidx.compose.ui.geometry.Offset(cx + w * 0.03f, cy - h * 0.02f),
            strokeWidth = w * 0.05f
        )

        // Blade 1
        val blade1 = Path().apply {
            moveTo(cx - w * 0.02f, cy)
            lineTo(cx + w * 0.18f, cy + h * 0.35f)
            lineTo(cx + w * 0.12f, cy + h * 0.36f)
            lineTo(cx, cy + h * 0.02f)
            close()
        }
        drawPath(
            path = blade1,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFCFD8DC), Color(0xFF90A4AE))
            )
        )

        // Blade 2
        val blade2 = Path().apply {
            moveTo(cx + w * 0.02f, cy)
            lineTo(cx - w * 0.18f, cy + h * 0.35f)
            lineTo(cx - w * 0.12f, cy + h * 0.36f)
            lineTo(cx, cy + h * 0.02f)
            close()
        }
        drawPath(
            path = blade2,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFECEFF1), Color(0xFFB0BEC5))
            )
        )

        // Pivot screw
        drawCircle(
            color = Color(0xFFFFB300),
            radius = w * 0.04f,
            center = androidx.compose.ui.geometry.Offset(cx, cy)
        )
        drawCircle(
            color = Color(0xFFFFA726),
            radius = w * 0.02f,
            center = androidx.compose.ui.geometry.Offset(cx, cy)
        )
    }
}

@Composable
fun VectorCheckUp(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2
        val cy = h / 2

        // Stethoscope Tubing
        val tubePath = Path().apply {
            moveTo(cx, cy - h * 0.05f)
            cubicTo(
                cx - w * 0.28f, cy + h * 0.05f,
                cx - w * 0.22f, cy + h * 0.32f,
                cx, cy + h * 0.28f
            )
        }
        drawPath(
            path = tubePath,
            color = Color(0xFF5F69C9),
            style = Stroke(width = w * 0.045f, cap = androidx.compose.ui.graphics.StrokeCap.Round)
        )

        // Metal Ear tubes
        val leftEarTube = Path().apply {
            moveTo(cx, cy - h * 0.05f)
            quadraticTo(cx - w * 0.14f, cy - h * 0.08f, cx - w * 0.15f, cy - h * 0.24f)
        }
        val rightEarTube = Path().apply {
            moveTo(cx, cy - h * 0.05f)
            quadraticTo(cx + w * 0.14f, cy - h * 0.08f, cx + w * 0.15f, cy - h * 0.24f)
        }
        drawPath(
            path = leftEarTube,
            color = Color(0xFFB0BEC5),
            style = Stroke(width = w * 0.035f, cap = androidx.compose.ui.graphics.StrokeCap.Round)
        )
        drawPath(
            path = rightEarTube,
            color = Color(0xFFB0BEC5),
            style = Stroke(width = w * 0.035f, cap = androidx.compose.ui.graphics.StrokeCap.Round)
        )

        // Eartips
        drawCircle(
            color = Color(0xFF37474F),
            radius = w * 0.035f,
            center = androidx.compose.ui.geometry.Offset(cx - w * 0.15f, cy - h * 0.26f)
        )
        drawCircle(
            color = Color(0xFF37474F),
            radius = w * 0.035f,
            center = androidx.compose.ui.geometry.Offset(cx + w * 0.15f, cy - h * 0.26f)
        )

        // Diaphragm
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFECEFF1), Color(0xFFB0BEC5))
            ),
            radius = w * 0.12f,
            center = androidx.compose.ui.geometry.Offset(cx, cy + h * 0.28f)
        )
        drawCircle(
            color = Color(0xFFFFB300),
            radius = w * 0.08f,
            center = androidx.compose.ui.geometry.Offset(cx, cy + h * 0.28f),
            style = Stroke(width = w * 0.02f)
        )
        drawCircle(
            color = Color(0xFF90A4AE),
            radius = w * 0.03f,
            center = androidx.compose.ui.geometry.Offset(cx, cy + h * 0.28f)
        )
    }
}

@Composable
fun VectorPetFood(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2
        val cy = h / 2

        // Pile of kibble
        drawCircle(
            color = Color(0xFF8D6E63),
            radius = w * 0.16f,
            center = androidx.compose.ui.geometry.Offset(cx, cy - h * 0.06f)
        )
        drawCircle(
            color = Color(0xFFA1887F),
            radius = w * 0.1f,
            center = androidx.compose.ui.geometry.Offset(cx - w * 0.1f, cy - h * 0.04f)
        )
        drawCircle(
            color = Color(0xFF70574E),
            radius = w * 0.1f,
            center = androidx.compose.ui.geometry.Offset(cx + w * 0.1f, cy - h * 0.04f)
        )

        // Bowl Body
        val bowlPath = Path().apply {
            moveTo(cx - w * 0.28f, cy)
            lineTo(cx + w * 0.28f, cy)
            lineTo(cx + w * 0.36f, cy + h * 0.26f)
            quadraticTo(cx + w * 0.34f, cy + h * 0.32f, cx + w * 0.28f, cy + h * 0.32f)
            lineTo(cx - w * 0.28f, cy + h * 0.32f)
            quadraticTo(cx - w * 0.34f, cy + h * 0.32f, cx - w * 0.36f, cy + h * 0.26f)
            close()
        }
        drawPath(
            path = bowlPath,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF7E8BF5), Color(0xFF4E5BC2))
            )
        )

        // Bowl Rim
        drawOval(
            color = Color(0xFF9FA8FA),
            topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.28f, cy - h * 0.04f),
            size = androidx.compose.ui.geometry.Size(w * 0.56f, h * 0.08f)
        )

        // Inner rim shadow
        drawOval(
            color = Color(0xFF5D4037),
            topLeft = androidx.compose.ui.geometry.Offset(cx - w * 0.24f, cy - h * 0.02f),
            size = androidx.compose.ui.geometry.Size(w * 0.48f, h * 0.06f)
        )

        // Bone shape
        val bonePath = Path().apply {
            val bx = cx
            val by = cy + h * 0.16f
            val bw = w * 0.14f
            val bh = h * 0.06f
            
            moveTo(bx - bw * 0.5f, by - bh * 0.2f)
            lineTo(bx + bw * 0.5f, by - bh * 0.2f)
            lineTo(bx + bw * 0.5f, by + bh * 0.2f)
            lineTo(bx - bw * 0.5f, by + bh * 0.2f)
            close()
        }
        drawPath(
            path = bonePath,
            color = Color.White
        )
        drawCircle(color = Color.White, radius = w * 0.045f, center = androidx.compose.ui.geometry.Offset(cx - w * 0.08f, cy + h * 0.12f))
        drawCircle(color = Color.White, radius = w * 0.045f, center = androidx.compose.ui.geometry.Offset(cx - w * 0.08f, cy + h * 0.20f))
        drawCircle(color = Color.White, radius = w * 0.045f, center = androidx.compose.ui.geometry.Offset(cx + w * 0.08f, cy + h * 0.12f))
        drawCircle(color = Color.White, radius = w * 0.045f, center = androidx.compose.ui.geometry.Offset(cx + w * 0.08f, cy + h * 0.20f))
    }
}
