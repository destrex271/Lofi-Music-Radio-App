@file:OptIn(ExperimentalMaterialApi::class)

package com.example.lofiplayer

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lofiplayer.ui.theme.LofiPlayerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import java.net.URI
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

// Spotify Imports



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LofiPlayerTheme {
                val sysUiController = rememberSystemUiController()
                SideEffect {
                    sysUiController.setStatusBarColor(
                        color = Color.Black,
                        darkIcons = true
                    )
                }
                // this.connect();
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.primarySurface
                ) {
                    mainUI();
                }
            }
        }
    }
}


var playingStat = mutableStateOf("Paused")
var radname = mutableStateOf("Star wars Lofi")
var stat = mutableStateOf("Connected")
var symbol = mutableStateOf(">")
var primStat = 0
var choice = mutableStateOf(0)
// val url = "https://cdnsongs.com/music/data/Single_Track/202211/Her/128/Her_1.mp3"
var urls = HashMap<String, String>();
val url = "https://res.cloudinary.com/ds7xs3pq1/video/upload/v1669931787/X2Download.app_-_Darth_Vader_s_Lofi_beats_to_relax_study_to___Star_Wars_Lofi_Chill_Mix_256_kbps_stx51q.mp3"
var mediaPlayer : MediaPlayer = MediaPlayer();

@Composable
fun setMedia(ch: Int){
    println("Starting")
    mediaPlayer.setDataSource(LocalContext.current, Uri.parse(url))
    mediaPlayer.prepare()
    mediaPlayer.isLooping = true;
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
}

@Composable
fun mainUI(){
    val navController = rememberNavController()
    urls.put(radname.value, url)
    val silkscreen_bold = FontFamily(
        Font(resId = R.font.silkscreen_bold, weight = FontWeight.Bold, style = FontStyle.Normal)
    )
    val silkscreen_reg = FontFamily(
        Font(resId = R.font.qilkscreen_regular, weight = FontWeight.Normal, style = FontStyle.Normal)
    )
    NavHost(navController = navController, startDestination = "splash"){
        composable(route = "splash") {
            splash(font = silkscreen_bold, nvc = navController)
        }
        composable(route = "mainBody"){
            mainContent(silkscreen_bold = silkscreen_bold, silkscreen_reg = silkscreen_reg)
        }
    }
}

@Composable
fun mainContent(silkscreen_bold: FontFamily, silkscreen_reg: FontFamily){
    Box(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .background(color = Color.Black)
                .blur(15.dp)
                .fillMaxSize()) {
            content();
        }
        body(head = silkscreen_bold, body = silkscreen_reg)
    }
}

@Composable
fun content(){
    val imgModifier = Modifier.size(1000.dp)
    Image(
        painter = painterResource(id = R.drawable.back),
        contentDescription = "Lofi Girl Background Image",
        contentScale = ContentScale.FillHeight,
        modifier = imgModifier,
        alpha = 0.3999F
    )
}
@Composable
fun body(head: FontFamily, body: FontFamily){
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        heading(font = head)
        buttons(font = body)
    }
}

fun playRad(){
    println("Hello")
    primStat = if(primStat == 0) 1 else 0
    playingStat.value = if(primStat == 0) "Paused" else "Playing"
    symbol.value = if(primStat == 0) ">" else "||"
    if(primStat == 0){
        mediaPlayer.pause();
    }else{
        mediaPlayer.start();
    }
}

@Composable
fun RadioVal(key: String){
    Card(
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
    ) {
        Button(onClick = { radname.value = urls[key].toString()}) {

        }
    }
}

@Composable
fun heading(font: FontFamily){
   Column(
       modifier = Modifier
           .fillMaxWidth()
           .padding(0.dp, 100.dp, 0.dp, 0.dp),
       verticalArrangement = Arrangement.spacedBy(60.dp),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Text(text = playingStat.value, fontSize = 50.sp, color = Color.White, fontFamily = font)
       Box(
           // Modifier.padding(0.dp, 150.dp, 0.dp, 0.dp)
       ) {
           OutlinedButton(
               onClick = {
                   playRad()
               },
               modifier = Modifier
                   .size(150.dp),
               shape = CircleShape,
               colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary)
           ) {
               Text(
                   text = symbol.value,
                   color = MaterialTheme.colors.secondary,
                   fontSize = 90.sp,
               )
           }
       }

   }
}

@OptIn(ExperimentalTime::class)
@Composable
fun splash(font: FontFamily, nvc: NavController){
    println("In splash")
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    setMedia(ch = 0)
    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }))
        // Customize the delay time
        delay(2000L)
        nvc.navigate("mainBody")
    }
    content()
    Box(
        Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lofi Radio",
                fontFamily = font,
                fontSize = 50.sp,
                modifier = Modifier.scale(scale.value)
            )
        }
    }
    println("Outsplash")
}

@Composable
fun buttons(font: FontFamily){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Current Radio",
                color = Color.White,
                fontSize = 27.sp,
                fontFamily = font
            )
            Text(
                text = radname.value,
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = font
            )
            Text(
                text = stat.value,
                color = Color.DarkGray,
                fontSize = 25.sp,
                fontFamily = font
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LofiPlayerTheme {
        mainUI()
    }
}