package com.example.androidui2.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Data class for Interest items
data class InterestItem(
    val title: String,
    val icon: ImageVector,
    val isCompleted: Boolean = false,
    val category: String = ""
)

// Modern Interest Card Component
@Composable
fun InterestCard(
    item: InterestItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                isPressed = true
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with gradient background
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(PrimaryAccent, SecondaryAccent)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Title and category
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                if (item.category.isNotEmpty()) {
                    Text(
                        text = item.category,
                        fontSize = 14.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Status indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (item.isCompleted) SuccessColor else WarningColor.copy(alpha = 0.2f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (item.isCompleted) Icons.Outlined.CheckCircle else Icons.Outlined.Add,
                    contentDescription = if (item.isCompleted) "Completed" else "Add",
                    tint = if (item.isCompleted) Color.White else WarningColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Modern Bottom Navigation Component
@Composable
fun ModernBottomNavigation(
    modifier: Modifier = Modifier
) {
    val navigationItems = listOf(
        Triple("For You", Icons.Outlined.Home, false),
        Triple("Shared", Icons.Outlined.Share, false),
        Triple("Interested", Icons.Outlined.Favorite, true)
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = CardBackground,
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationItems.forEach { (label, icon, isSelected) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { }
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) {
                                    Brush.linearGradient(
                                        colors = listOf(PrimaryAccent, SecondaryAccent)
                                    )
                                } else {
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color.Gray.copy(alpha = 0.1f),
                                            Color.Gray.copy(alpha = 0.05f)
                                        )
                                    )
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = if (isSelected) Color.White else TextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        color = if (isSelected) PrimaryAccent else TextSecondary,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
fun AndroidUI_2() {
    val interests = listOf(
        InterestItem("Accessibility", Icons.Outlined.AccountCircle, true, "UI/UX"),
        InterestItem("Android Auto", Icons.Outlined.Build, false, "Automotive"),
        InterestItem("Android Studio & Tools", Icons.Outlined.Menu, true, "Development"),
        InterestItem("Android TV", Icons.Outlined.DateRange, false, "TV & Entertainment"),
        InterestItem("Architecture", Icons.Outlined.MailOutline, true, "Development"),
        InterestItem("Camera & Media", Icons.Outlined.Info, false, "Multimedia"),
        InterestItem("Compose", Icons.Outlined.ShoppingCart, true, "UI Framework")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BackgroundGradientStart, BackgroundGradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Interests",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Discover what matters to you",
                        fontSize = 16.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Interests List
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(interests) { interest ->
                    InterestCard(
                        item = interest,
                        onClick = {
                            // Handle click - you can add navigation or other actions here
                        }
                    )
                }
            }
        }

        // Bottom Navigation
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            ModernBottomNavigation()
        }
    }
}