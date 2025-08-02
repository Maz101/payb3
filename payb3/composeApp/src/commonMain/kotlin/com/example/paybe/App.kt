package com.example.paybe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding // Import for padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar // Or TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold // Import Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults // For styling/colors
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// remove the safeContentPadding as Scaffold handles it
// import androidx.compose.foundation.layout.safeContentPadding
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import paybe.composeapp.generated.resources.Res
import paybe.composeapp.generated.resources.compose_multiplatform // Assuming this is a painter resource
import paybe.composeapp.generated.resources.inter_medium
import paybe.composeapp.generated.resources.inter_24pt_regular
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton // Good for transparent with border
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


@OptIn(ExperimentalMaterial3Api::class) // Required for CenterAlignedTopAppBar and Scaffold
@Composable
@Preview
fun App() {
    val interMediumFontFamily = FontFamily(Font(Res.font.inter_medium))
    val inter_regular_24 = FontFamily(Font(Res.font.inter_24pt_regular))

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar( // Or use TopAppBar for start-aligned title
                    modifier = Modifier.defaultMinSize(minHeight = 120.dp),
                    title = {
                        Text("Choose \npayment device",
                            fontFamily = inter_regular_24,
                            fontSize = 40.sp,
                            lineHeight = 52.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                           )
                    },

                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors( // Customize colors
                        containerColor = Color.White,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { innerPadding -> // Content of your screen goes here
            var showContent by remember { mutableStateOf(false) }
            Column(
                // Apply padding from the Scaffold to avoid content overlapping with the TopAppBar
                modifier = Modifier
                    .padding(innerPadding) // Apply padding from Scaffold
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
            ) {
                CircularTransparentButtonWithText(text= "Payment \nRing",onClick = { showContent = !showContent })
//                AnimatedVisibility(showContent) {
//                    val greeting = remember { Greeting().greet() }
//                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                        Image(
//                            painter = painterResource(Res.drawable.compose_multiplatform), // Assuming 'compose_multiplatform' is a drawable
//                            contentDescription = "Compose Multiplatform Logo"
//                        )
//                        Text("Compose: $greeting", fontFamily = interMediumFontFamily)
//                    }
//                }
            }



        }
    }
}

@Composable
fun CircularTransparentButtonWithText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: androidx.compose.ui.unit.Dp = 48.dp, // Size of the circular button
    buttonBorderColor: Color = Color.Black, // Color of the circle's border
    buttonBorderWidth: androidx.compose.ui.unit.Dp = 1.5.dp,
    icon: @Composable (() -> Unit)? = null, // Optional icon for the button
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textFontSize: androidx.compose.ui.unit.TextUnit = 40.sp,
    spacing: androidx.compose.ui.unit.Dp = 12.dp // Space between button and text
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp) // Overall padding for the Row element
    ) {
        // Circular Transparent Button

        Text(
            text = text,
            color = textColor,
            fontSize = textFontSize,
            lineHeight = 52.sp,
            textAlign = TextAlign
                .Start,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.width(spacing))

        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.size(buttonSize),
            shape = CircleShape,
            border = BorderStroke(buttonBorderWidth, buttonBorderColor),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent, // Transparent background
                contentColor = buttonBorderColor     // Color for the icon if provided
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp) // Remove default button padding to center icon
        ) {
            if (icon != null) {
                icon()
            } else {
                // You can have a default placeholder or nothing if no icon is provided
                // For example, a simple Box might be enough if you just want the circle
                Box {}
            }
        }
    }
}



