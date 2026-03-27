package com.switchcodeur.hardm3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.switchcodeur.hardm3.theme.MyApplicationTheme
import com.switchcodeur.hardm3.components.HomeIcon
import com.switchcodeur.hardm3.components.SettingsIcon
import com.switchcodeur.hardm3.services.RadioService
import com.switchcodeur.hardm3.pages.HomePage
import com.switchcodeur.hardm3.pages.SettingsPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startForegroundService(Intent(this, RadioService::class.java))

        enableEdgeToEdge()
        setContent {
            val pagerState = rememberPagerState(pageCount = { 2 })
            val scope = rememberCoroutineScope()

            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { MyTopBar() },
                    bottomBar = {
                        MyNavigationBar(
                            index = pagerState.currentPage,
                            handler = { page ->
                                scope.launch {
                                    pagerState.animateScrollToPage(page)
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.padding(innerPadding)
                    ) { page ->
                        when (page) {
                            0 -> HomePage()
                            1 -> SettingsPage()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, RadioService::class.java))
        super.onDestroy()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Launcher Foreground",
                tint = Color.Unspecified
            )
        },
        title = {
            Text(
                stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold
            )
        }
    )
}

@Composable
fun MyNavigationBar(index: Int, handler: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = index == 0,
            onClick = { handler(0) },
            icon = { HomeIcon(index == 0) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = index == 1,
            onClick = { handler(1) },
            icon = { SettingsIcon(index == 1) },
            label = { Text("Settings") }
        )
    }
}