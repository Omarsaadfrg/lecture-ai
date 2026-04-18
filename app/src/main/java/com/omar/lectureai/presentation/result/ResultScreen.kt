package com.omar.lectureai.presentation.result

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ──────────────────────────────────────────────
//  COLOR PALETTE
// ──────────────────────────────────────────────
private object LectureColors {
    val Background    = Color(0xFF0A0A0F)
    val Surface       = Color(0xFF13131A)
    val SurfaceHigh   = Color(0xFF1C1C27)
    val Accent        = Color(0xFF00F5A0)
    val AccentDim     = Color(0xFF00C87A)
    val AccentGlow    = Color(0x3000F5A0)
    val TextPrimary   = Color(0xFFF0F0F5)
    val TextSecondary = Color(0xFF8A8A9A)
    val Border        = Color(0xFF2A2A38)
    val TabInactive   = Color(0xFF3A3A4A)
    val Highlight     = Color(0xFF1A2A22)
}

// ──────────────────────────────────────────────
//  DATA MODELS
// ──────────────────────────────────────────────
data class TranscriptBlock(
    val id: Int,
    val timestamp: String,
    val text: String
)

enum class LectureTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    TRANSCRIPT("Transcript", Icons.Rounded.Article),
    SUMMARY("Summary", Icons.Rounded.Summarize),
    QUESTIONS("Questions", Icons.Rounded.Quiz)
}

// ──────────────────────────────────────────────
//  MAIN SCREEN
// ──────────────────────────────────────────────
@Composable
fun ResultScreen(
    transcriptBlocks: List<TranscriptBlock> = sampleTranscript(),
    onCopy: () -> Unit = {},
    onSave: () -> Unit = {},
    onExportPdf: () -> Unit = {},
    onExportTxt: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(LectureTab.TRANSCRIPT) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LectureColors.Background)
    ) {
        // Ambient glow blob in the top area
        AmbientGlow(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = (-60).dp, y = (-40).dp)
        )

        Column(modifier = Modifier.fillMaxSize()) {

            // ── Top Bar ──
            TopBar(onHomeClick = onBack)
            // ── Tab Row ──
            TabRow(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            // ── Action Bar ──
            ActionBar(
                onCopy = onCopy,
                onSave = onSave,
                onExportPdf = onExportPdf,
                onExportTxt = onExportTxt
            )

            // ── Content ──
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn(tween(300)) togetherWith fadeOut(tween(200))
                },
                label = "tab_content"
            ) { tab ->
                when (tab) {
                    LectureTab.TRANSCRIPT -> TranscriptContent(blocks = transcriptBlocks)
                    LectureTab.SUMMARY    -> PlaceholderContent("Summary coming soon…")
                    LectureTab.QUESTIONS  -> PlaceholderContent("Questions coming soon…")
                }
            }
        }
    }
}

// ──────────────────────────────────────────────
//  TOP BAR
// ──────────────────────────────────────────────
@Composable
private fun TopBar(onHomeClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Lecture",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = LectureColors.TextPrimary,
                    letterSpacing = (-0.5).sp
                )
            )
            Text(
                text = "AI",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = LectureColors.Accent,
                    letterSpacing = (-0.5).sp
                )
            )
        }

        // Home button
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(LectureColors.SurfaceHigh)
                .border(1.dp, LectureColors.Border, CircleShape)
                .clickable { onHomeClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "Home",
                tint = LectureColors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ──────────────────────────────────────────────
//  TAB ROW
// ──────────────────────────────────────────────
@Composable
private fun TabRow(
    selectedTab: LectureTab,
    onTabSelected: (LectureTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(LectureColors.Surface)
            .border(1.dp, LectureColors.Border, RoundedCornerShape(16.dp))
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        LectureTab.values().forEach { tab ->
            val isSelected = tab == selectedTab
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) LectureColors.Accent else Color.Transparent,
                animationSpec = tween(250),
                label = "tab_bg_${tab.name}"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) LectureColors.Background else LectureColors.TextSecondary,
                animationSpec = tween(250),
                label = "tab_text_${tab.name}"
            )

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(bgColor)
                    .clickable { onTabSelected(tab) }
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.label,
                    tint = textColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = tab.label,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = textColor
                    )
                )
            }
        }
    }
}

// ──────────────────────────────────────────────
//  ACTION BAR (Copy / Save / PDF / TXT)
// ──────────────────────────────────────────────
@Composable
private fun ActionBar(
    onCopy: () -> Unit,
    onSave: () -> Unit,
    onExportPdf: () -> Unit,
    onExportTxt: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionButton(
            icon = Icons.Rounded.CopyAll,
            label = "Copy",
            isPrimary = true,
            onClick = onCopy,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            icon = Icons.Rounded.BookmarkAdd,
            label = "Save",
            onClick = onSave,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            icon = Icons.Rounded.PictureAsPdf,
            label = "PDF",
            onClick = onExportPdf,
            modifier = Modifier.weight(1f)
        )
        ActionButton(
            icon = Icons.Rounded.FileOpen,
            label = "TXT",
            onClick = onExportTxt,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isPrimary: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.94f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "btn_scale"
    )

    val bg = if (isPrimary) LectureColors.Accent else LectureColors.SurfaceHigh
    val textColor = if (isPrimary) LectureColors.Background else LectureColors.TextPrimary

    Box(
        modifier = modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .border(
                width = if (isPrimary) 0.dp else 1.dp,
                color = if (isPrimary) Color.Transparent else LectureColors.Border,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                pressed = true
                onClick()
            }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = textColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            )
        }
    }

    // Reset press state
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(120)
            pressed = false
        }
    }
}

// ──────────────────────────────────────────────
//  TRANSCRIPT CONTENT
// ──────────────────────────────────────────────
@Composable
private fun TranscriptContent(blocks: List<TranscriptBlock>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        // Section Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(LectureColors.Accent)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Full Transcript",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LectureColors.TextPrimary,
                    letterSpacing = (-0.3).sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            // Word count badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(LectureColors.Highlight)
                    .border(1.dp, LectureColors.AccentDim.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${blocks.sumOf { it.text.split(" ").size }} words",
                    style = TextStyle(
                        fontSize = 11.sp,
                        color = LectureColors.AccentDim,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        // Blocks list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(blocks, key = { it.id }) { block ->
                TranscriptBlockCard(block = block)
            }
        }
    }
}

@Composable
private fun TranscriptBlockCard(block: TranscriptBlock) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(block.id * 80L)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 3 }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(LectureColors.Surface)
                .border(1.dp, LectureColors.Border, RoundedCornerShape(16.dp))
        ) {
            // Left accent line
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 0.dp)
                    .width(3.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                    .background(LectureColors.Accent.copy(alpha = 0.5f))
            )

            Column(modifier = Modifier.padding(start = 18.dp, end = 16.dp, top = 14.dp, bottom = 14.dp)) {
                // Timestamp
                Text(
                    text = block.timestamp,
                    style = TextStyle(
                        fontSize = 11.sp,
                        color = LectureColors.AccentDim,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                // Transcript text
                Text(
                    text = block.text,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = LectureColors.TextPrimary,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

// ──────────────────────────────────────────────
//  PLACEHOLDER (Summary / Questions)
// ──────────────────────────────────────────────
@Composable
private fun PlaceholderContent(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = TextStyle(
                fontSize = 16.sp,
                color = LectureColors.TextSecondary,
                textAlign = TextAlign.Center
            )
        )
    }
}

// ──────────────────────────────────────────────
//  AMBIENT GLOW DECORATION
// ──────────────────────────────────────────────
@Composable
private fun AmbientGlow(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(300.dp)
            .blur(80.dp)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        LectureColors.Accent.copy(alpha = 0.08f),
                        Color.Transparent
                    )
                )
            )
    )
}

// ──────────────────────────────────────────────
//  SAMPLE DATA
// ──────────────────────────────────────────────
fun sampleTranscript(): List<TranscriptBlock> = listOf(
    TranscriptBlock(
        id = 1,
        timestamp = "00:00:00",
        text = "Welcome to today's lecture on Machine Learning fundamentals. We'll be covering supervised and unsupervised learning, neural networks, and practical applications in modern AI systems."
    ),
    TranscriptBlock(
        id = 2,
        timestamp = "00:01:15",
        text = "Let's start with supervised learning. This is a type of machine learning where we train our model using labeled data. The algorithm learns from the training dataset and makes predictions on new, unseen data. Common examples include image classification, spam detection, and sentiment analysis."
    ),
    TranscriptBlock(
        id = 3,
        timestamp = "00:03:42",
        text = "Unsupervised learning, on the other hand, works with unlabeled data. The algorithm tries to find patterns and structures in the data without explicit guidance. Clustering algorithms like K-means and dimensionality reduction techniques like PCA are common examples."
    ),
    TranscriptBlock(
        id = 4,
        timestamp = "00:06:10",
        text = "Neural networks are inspired by the structure of the human brain. They consist of layers of interconnected nodes, or neurons, that process information and learn complex patterns from data."
    ),
    TranscriptBlock(
        id = 5,
        timestamp = "00:09:30",
        text = "Deep learning extends neural networks with many hidden layers, enabling models to learn hierarchical representations. This has led to breakthroughs in computer vision, natural language processing, and speech recognition."
    )
)

