package com.example.androidui2.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
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

// Modern Interest Card Component with enhanced animations
@Composable
fun InterestCard(
    item: InterestItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 16f else 8f,
        animationSpec = tween(durationMillis = 200)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                isPressed = true
                onClick()
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        isHovered = true
                        tryAwaitRelease()
                        isPressed = false
                        isHovered = false
                    }
                )
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
            // Icon with gradient background and pulse animation
            val infiniteTransition = rememberInfiniteTransition()
            val pulseScale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = if (item.isCompleted) 1.1f else 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .scale(pulseScale)
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

            // Title and category with improved layout
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    maxLines = 1
                )
                if (item.category.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PrimaryAccent.copy(alpha = 0.1f),
                        modifier = Modifier.width(80.dp)
                    ) {
                        Text(
                            text = item.category,
                            fontSize = 12.sp,
                            color = PrimaryAccent,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            maxLines = 1
                        )
                    }
                }
            }

            // Enhanced status indicator with animation
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = if (!item.isCompleted) 360f else 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (item.isCompleted) {
                            Brush.linearGradient(
                                colors = listOf(SuccessColor, SuccessColor.copy(alpha = 0.8f))
                            )
                        } else {
                            Brush.linearGradient(
                                colors = listOf(
                                    WarningColor.copy(alpha = 0.3f),
                                    WarningColor.copy(alpha = 0.1f)
                                )
                            )
                        }
                    )
                    .rotate(if (!item.isCompleted) rotation else 0f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (item.isCompleted) Icons.Outlined.CheckCircle else Icons.Outlined.Add,
                    contentDescription = if (item.isCompleted) "Completed" else "Add",
                    tint = if (item.isCompleted) Color.White else WarningColor,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

// Enhanced Bottom Navigation Component with notification badges
@Composable
fun ModernBottomNavigation(
    modifier: Modifier = Modifier
) {
    val navigationItems = listOf(
        Triple("For You", Icons.Outlined.Home, false),
        Triple("Shared", Icons.Outlined.Share, false),
        Triple("Interested", Icons.Outlined.Favorite, true)
    )

    val notificationCounts = mapOf(
        "For You" to 0,
        "Shared" to 2,
        "Interested" to 0
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = CardBackground,
        shadowElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationItems.forEach { (label, icon, isSelected) ->
                val notificationCount = notificationCounts[label] ?: 0

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { }
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(14.dp))
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

                        // Notification badge
                        if (notificationCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(WarningColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (notificationCount > 9) "9+" else notificationCount.toString(),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
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

// Search Bar Component
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .shadow(
                elevation = if (isFocused) 12.dp else 4.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        color = CardBackground
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { isFocused = it.isFocused },
            placeholder = {
                Text(
                    "Explore interests...",
                    color = TextSecondary,
                    fontSize = 17.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = PrimaryAccent,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = if (query.isNotEmpty()) {
                {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "Clear",
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            } else null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
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

    var searchQuery by remember { mutableStateOf("") }
    var showWelcome by remember { mutableStateOf(true) }

    // Auto-hide welcome message after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        showWelcome = false
    }

    // Filter interests based on search query
    val filteredInterests = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            interests
        } else {
            interests.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
            }
        }
    }

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
            // Enhanced Header with subtle animation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        // Animated sparkles icon
                        val infiniteTransition = rememberInfiniteTransition()
                        val sparkleAlpha by infiniteTransition.animateFloat(
                            initialValue = 0.3f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1500, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        Icon(
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .alpha(sparkleAlpha),
                            tint = PrimaryAccent
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "My Interests",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PrimaryAccent.copy(alpha = 0.1f),
                        modifier = Modifier.width(200.dp)
                    ) {
                        Text(
                            text = "Find what interests you most",
                            fontSize = 16.sp,
                            color = PrimaryAccent,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Quick Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val completedCount = interests.count { it.isCompleted }
                val totalCount = interests.size

                // Completed Stats
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardBackground)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "$completedCount",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SuccessColor
                    )
                    Text(
                        text = "Completed",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Total Stats
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardBackground)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "$totalCount",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryAccent
                    )
                    Text(
                        text = "Total",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Progress Indicator
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardBackground)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "${(completedCount.toFloat() / totalCount * 100).toInt()}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (completedCount.toFloat() / totalCount >= 0.7f) SuccessColor else WarningColor
                    )
                    Text(
                        text = "Progress",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Subtle divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                TextSecondary.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(vertical = 8.dp)
            )

            // Interests List or Empty State
            if (filteredInterests.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "No results",
                            modifier = Modifier.size(64.dp),
                            tint = TextSecondary.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No interests found",
                            fontSize = 18.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Try a different search term",
                            fontSize = 14.sp,
                            color = TextSecondary.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(filteredInterests) { interest ->
                        InterestCard(
                            item = interest,
                            onClick = {
                                // Handle click - you can add navigation or other actions here
                            }
                        )
                    }
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

        // Welcome overlay
        if (showWelcome) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .clickable { showWelcome = false },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(32.dp)
                        .shadow(16.dp, RoundedCornerShape(24.dp)),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Animated welcome icon
                        val welcomeTransition = rememberInfiniteTransition()
                        val welcomeScale by welcomeTransition.animateFloat(
                            initialValue = 0.8f,
                            targetValue = 1.2f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(800, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .scale(welcomeScale)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(PrimaryAccent, SecondaryAccent)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = "Welcome",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Welcome to Interests!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Discover your interests",
                            fontSize = 16.sp,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Tap anywhere to continue",
                            fontSize = 14.sp,
                            color = TextSecondary.copy(alpha = 0.7f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}