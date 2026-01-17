package com.example.androidui2.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import kotlinx.coroutines.launch

// Enhanced Floating Action Button Component
@Composable
fun AddInterestFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    FloatingActionButton(
        onClick = {
            isPressed = true
            onClick()
            // Reset press state after animation
            kotlinx.coroutines.GlobalScope.launch {
                delay(100)
                isPressed = false
            }
        },
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = 12.dp,
                shape = CircleShape,
                spotColor = PrimaryAccent.copy(alpha = 0.3f)
            ),
        containerColor = PrimaryAccent,
        contentColor = Color.White,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add new interest",
            modifier = Modifier.size(28.dp)
        )
    }
}

// Category Filter Chips Component
@Composable
fun CategoryFilterChips(
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "UI-UX", "Development", "Multimedia", "Entertainment", "Lifestyle")

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            val isSelected = selectedCategory == category || (category == "All" && selectedCategory == null)

            Card(
                modifier = Modifier
                    .clickable {
                        onCategorySelected(if (category == "All") null else category)
                    }
                    .border(
                        width = 1.dp,
                        color = if (isSelected) PrimaryAccent.copy(alpha = 0.3f) else TextSecondary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) PrimaryAccent.copy(alpha = 0.1f) else CardBackground
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Selected",
                            modifier = Modifier.size(16.dp),
                            tint = PrimaryAccent
                        )
                    }
                    Text(
                        text = category,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) PrimaryAccent else TextSecondary
                    )
                }
            }
        }
    }
}

// Data class for Interest items
data class InterestItem(
    val title: String,
    val icon: ImageVector,
    val isCompleted: Boolean = false,
    val category: String = "",
    val isNew: Boolean = false
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
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Title and category with improved layout
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    if (item.isNew) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = WarningColor,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = "New Topic 2",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                if (item.category.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PrimaryAccent.copy(alpha = 0.2f),
                        modifier = Modifier.width(32.dp)
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
                                colors = listOf(SuccessColor, SuccessColor.copy(alpha = 0.6f))
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
                    contentDescription = if (item.isCompleted) "Completed by you" else "Add it",
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
                    contentDescription = "Search..",
                    tint = PrimaryAccent,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = if (query.isNotEmpty()) {
                {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "Clear...",
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
        InterestItem("Accessibilities", Icons.Outlined.AccountCircle, true, "UI/UX"),
        InterestItem("Android Auto", Icons.Outlined.Build, false, "Automotive"),
        InterestItem("Android Studio Tools", Icons.Outlined.Menu, true, "Development"),
        InterestItem("Android TVs", Icons.Outlined.DateRange, false, "TV & Entertainment"),
        InterestItem("Architecture", Icons.Outlined.MailOutline, true, "Development"),
        InterestItem("Camera and Media 2", Icons.Outlined.Info, false, "Multimedia"),
        InterestItem("Compose", Icons.Outlined.ShoppingCart, true, "UI Framework"),
        InterestItem("Kotlin 3", Icons.Outlined.Menu, true, "Development"),
        InterestItem("Material Design", Icons.Outlined.Star, true, "UI/UX"),
        InterestItem("Wear Operating System 2", Icons.Outlined.DateRange, true, "Wearables", true),
        InterestItem("Firebase control", Icons.Outlined.Info, true, "Backend", true),
        InterestItem("Machine Learning", Icons.Outlined.MailOutline, false, "AI/ML", true),
        InterestItem("Security", Icons.Outlined.Lock, true, "Development"),
        InterestItem("Testing 2", Icons.Outlined.Clear, false, "Development"),
        InterestItem("Performance", Icons.Outlined.Search, true, "Development"),
        InterestItem("Gaming", Icons.Outlined.ShoppingCart, false, "Entertainment"),
        InterestItem("Health & Fitness", Icons.Outlined.Favorite, true, "Lifestyle", true),
        InterestItem("Productivity", Icons.Outlined.CheckCircle, true, "Tools", true)
    )

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showWelcome by remember { mutableStateOf(true) }

    // Auto-hide welcome message after 3 seconds
    LaunchedEffect(Unit) {
        delay(3000)
        showWelcome = false
    }

    // Filter interests based on search query and category
    val filteredInterests = remember(searchQuery, selectedCategory) {
        interests.filter { interest ->
            val matchesSearch = searchQuery.isBlank() ||
                    interest.title.contains(searchQuery, ignoreCase = false) ||
                    interest.category.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == null || interest.category == selectedCategory
            matchesSearch && matchesCategory
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
                                animation = tween(1400, easing = LinearEasing),
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
                            text = "My Interest",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            modifier = Modifier.weight(1f)
                        )

                        // Mini settings button
                        IconButton(
                            onClick = {
                                // Handle settings - could open settings screen
                            },
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(TextSecondary.copy(alpha = 0.1f))
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings",
                                modifier = Modifier.size(18.dp),
                                tint = TextSecondary
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PrimaryAccent.copy(alpha = 0.2f),
                        modifier = Modifier.width(200.dp)
                    ) {
                        Text(
                            text = "Find what interests you most",
                            fontSize = 16.sp,
                            color = PrimaryAccent,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Category Filter Chips
            CategoryFilterChips(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                modifier = Modifier.padding(bottom = 8.dp)
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
                    // Circular Progress Indicator
                    val progressValue = completedCount.toFloat() / totalCount
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = { progressValue },
                            modifier = Modifier.fillMaxSize(),
                            color = if (progressValue >= 0.7f) SuccessColor else WarningColor,
                            strokeWidth = 4.dp,
                            trackColor = TextSecondary.copy(alpha = 0.2f)
                        )
                        Text(
                            text = "${(progressValue * 100).toInt()}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (progressValue >= 0.7f) SuccessColor else WarningColor
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Progress",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Pending Stats
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardBackground)
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "${totalCount - completedCount}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarningColor
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = TextSecondary
                        )
                        Text(
                            text = "To Do",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
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
                    // Pull to refresh hint
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = TextSecondary.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Pull to refresh",
                                fontSize = 12.sp,
                                color = TextSecondary.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }

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

        // Floating Action Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 100.dp)
        ) {
            AddInterestFAB(
                onClick = {
                    // Handle add new interest - could show dialog or navigate to add screen
                }
            )
        }

        // Welcome overlay
        if (showWelcome) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable { showWelcome = true },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(32.dp)
                        .shadow(16.dp, RoundedCornerShape(12.dp)),
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
                                contentDescription = "Welcome Back",
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
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Explore your passion",
                            fontSize = 16.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Ready to explore?",
                            fontSize = 14.sp,
                            color = TextSecondary.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}