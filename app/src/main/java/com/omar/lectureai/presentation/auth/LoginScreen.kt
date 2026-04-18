package com.omar.lectureai.presentation.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

// ─────────────────────────────────────────────
//  COLORS
// ─────────────────────────────────────────────
private object LC {
    val Background  = Color(0xFF0A0A0F)
    val SurfaceHigh = Color(0xFF1C1C27)
    val Accent      = Color(0xFF00F5A0)
    val AccentDim   = Color(0xFF00C87A)
    val AccentGlow  = Color(0x3500F5A0)
    val TextPri     = Color(0xFFF0F0F5)
    val TextSec     = Color(0xFF8A8A9A)
    val TextHint    = Color(0xFF4A4A5A)
    val Border      = Color(0xFF2A2A38)
    val Error       = Color(0xFFFF5C7A)
}

// ─────────────────────────────────────────────
//  SCREEN
// ─────────────────────────────────────────────
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onCreateAccount: () -> Unit = {}
) {
    var email         by remember { mutableStateOf("") }
    var password      by remember { mutableStateOf("") }
    var showPassword  by remember { mutableStateOf(false) }
    var isLoading     by remember { mutableStateOf(false) }
    var emailError    by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val focusManager  = LocalFocusManager.current

    fun doLogin() {
        emailError    = if (email.isBlank()) "Email is required"
        else if (!email.contains("@")) "Enter a valid email"
        else null
        passwordError = if (password.length < 6) "At least 6 characters" else null
        if (emailError == null && passwordError == null) isLoading = true
    }

    LaunchedEffect(isLoading) {
        if (isLoading) { delay(1800); isLoading = false; onLoginSuccess() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LC.Background)
    ) {
        // Top glow
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-30).dp)
                .size(300.dp)
                .blur(90.dp)
                .background(Brush.radialGradient(listOf(LC.AccentGlow, Color.Transparent)))
        )

        FloatingParticles()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(72.dp))

            AppIcon()

            Spacer(Modifier.height(28.dp))

            Text(
                text = "Welcome to",
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold,
                    color = LC.TextPri, textAlign = TextAlign.Center, letterSpacing = (-0.5).sp)
            )
            Text(
                text = "LectureAI",
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.ExtraBold,
                    color = LC.Accent, textAlign = TextAlign.Center, letterSpacing = (-0.5).sp)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Turn lectures into notes instantly",
                style = TextStyle(fontSize = 15.sp, color = LC.TextSec, textAlign = TextAlign.Center)
            )

            Spacer(Modifier.height(44.dp))

            // ── Email ──
            AuthField(
                label = "Email",
                value = email,
                onValueChange = { email = it; emailError = null },
                placeholder = "your@email.com",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                error = emailError
            )

            Spacer(Modifier.height(16.dp))

            // ── Password ──
            AuthField(
                label = "Password",
                value = password,
                onValueChange = { password = it; passwordError = null },
                placeholder = "••••••••",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                isPassword = true,
                showPassword = showPassword,
                onTogglePassword = { showPassword = !showPassword },
                onDone = { focusManager.clearFocus(); doLogin() },
                error = passwordError
            )

            Spacer(Modifier.height(32.dp))

            LoginButton(isLoading = isLoading, onClick = { focusManager.clearFocus(); doLogin() })

            Spacer(Modifier.height(28.dp))

            Row {
                Text("Don't have an account? ",
                    style = TextStyle(fontSize = 14.sp, color = LC.TextSec))
                Text("Create one",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = LC.Accent),
                    modifier = Modifier.clickable { onCreateAccount() })
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text = "By continuing, you agree to our Terms of Service",
                style = TextStyle(fontSize = 12.sp, color = LC.TextHint, textAlign = TextAlign.Center)
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

// ─────────────────────────────────────────────
//  APP ICON
// ─────────────────────────────────────────────
@Composable
private fun AppIcon() {
    val inf = rememberInfiniteTransition(label = "icon")
    val glowScale by inf.animateFloat(0.9f, 1.1f,
        infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = "gs")

    Box(contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.size((80 * glowScale).dp).blur(20.dp)
            .background(Brush.radialGradient(listOf(LC.AccentGlow, Color.Transparent))))
        Box(
            modifier = Modifier.size(68.dp).clip(RoundedCornerShape(20.dp))
                .background(Brush.linearGradient(
                    listOf(Color(0xFF0D2A1E), Color(0xFF0A1A14)),
                    start = Offset(0f, 0f), end = Offset(68f, 68f)))
                .border(1.dp, LC.Accent.copy(alpha = 0.3f), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.AutoAwesome, "LectureAI", tint = LC.Accent, modifier = Modifier.size(32.dp))
        }
    }
}

// ─────────────────────────────────────────────
//  AUTH FIELD  — uses Material3 OutlinedTextField
// ─────────────────────────────────────────────
@Composable
private fun AuthField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onTogglePassword: () -> Unit = {},
    onDone: () -> Unit = {},
    error: String? = null
) {
    val focusColors = TextFieldDefaults.colors(
        focusedContainerColor   = LC.SurfaceHigh,
        unfocusedContainerColor = LC.SurfaceHigh,
        focusedTextColor        = LC.TextPri,
        unfocusedTextColor      = LC.TextPri,
        focusedIndicatorColor   = LC.Accent,
        unfocusedIndicatorColor = LC.Border,
        errorIndicatorColor     = LC.Error,
        focusedLabelColor       = LC.Accent,
        unfocusedLabelColor     = LC.TextSec,
        errorLabelColor         = LC.Error,
        cursorColor             = LC.Accent,
        errorContainerColor     = LC.SurfaceHigh,
        errorCursorColor        = LC.Error
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = { Text(placeholder, style = TextStyle(color = LC.TextHint, fontSize = 15.sp)) },
        singleLine = true,
        isError = error != null,
        supportingText = error?.let { { Text(it, color = LC.Error, fontSize = 12.sp) } },
        visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        trailingIcon = if (isPassword) ({
            IconButton(onClick = onTogglePassword) {
                Icon(
                    imageVector = if (showPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                    contentDescription = "Toggle password",
                    tint = LC.TextSec
                )
            }
        }) else null,
        shape = RoundedCornerShape(14.dp),
        colors = focusColors,
        textStyle = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal)
    )
}

// ─────────────────────────────────────────────
//  LOGIN BUTTON
// ─────────────────────────────────────────────
@Composable
private fun LoginButton(isLoading: Boolean, onClick: () -> Unit) {
    val scale by animateFloatAsState(
        targetValue = if (isLoading) 0.97f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "btn_scale"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(28.dp))
            .background(Brush.horizontalGradient(
                if (isLoading) listOf(LC.AccentDim, LC.AccentDim)
                else           listOf(LC.Accent, LC.AccentDim)
            ))
            .clickable(enabled = !isLoading) { onClick() }
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(Modifier.size(20.dp), color = LC.Background, strokeWidth = 2.5.dp)
                Spacer(Modifier.width(12.dp))
                Text("Signing in...", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LC.Background))
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Login, null, tint = LC.Background, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
                Text("Login", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LC.Background))
            }
        }
    }
}

// ─────────────────────────────────────────────
//  FLOATING PARTICLES
// ─────────────────────────────────────────────
private data class Particle(val x: Float, val y: Float, val size: Float, val speed: Float, val alpha: Float)

@Composable
private fun FloatingParticles() {
    val particles = remember {
        List(18) {
            Particle(
                x     = Random.nextFloat(),
                y     = Random.nextFloat(),
                size  = Random.nextFloat() * 3f + 2f,
                speed = Random.nextFloat() * 4000f + 3000f,
                alpha = Random.nextFloat() * 0.4f + 0.1f
            )
        }
    }
    particles.forEach { p ->
        val inf = rememberInfiniteTransition(label = "p${p.x}")
        val offsetY by inf.animateFloat(0f, -30f,
            infiniteRepeatable(tween(p.speed.toInt(), easing = LinearEasing), RepeatMode.Reverse),
            label = "py${p.x}")
        val alpha by inf.animateFloat(p.alpha * 0.4f, p.alpha,
            infiniteRepeatable(tween((p.speed * 0.7f).toInt(), easing = FastOutSlowInEasing), RepeatMode.Reverse),
            label = "pa${p.x}")
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color  = Color(0xFF00F5A0).copy(alpha = alpha),
                radius = p.size.dp.toPx(),
                center = Offset(p.x * size.width, p.y * size.height + offsetY.dp.toPx())
            )
        }
    }
}