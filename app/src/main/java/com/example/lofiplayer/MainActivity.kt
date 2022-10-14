package com.example.lofiplayer

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
import com.example.lofiplayer.ui.theme.LofiPlayerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
// Spotify Imports
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;


var spotifyAppRemote: SpotifyAppRemote? = null;

class MainActivity : ComponentActivity() {

    fun connect(): SpotifyApp{
        val clientId = "a9fa0def4cb4447293df558341852aa3";
        val redirectUrl = "https://open.spotify.com/";
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUrl)
            .showAuthView(true)
            .build();
        println(connectionParams)
        SpotifyAppRemote.connect(applicationContext, connectionParams, object:
            Connector.ConnectionListener{
                override fun onConnected(appRemote: SpotifyAppRemote?) {
                    spotifyAppRemote = appRemote
                }

                override fun onFailure(error: Throwable?) {
                    println("Failed!")
                }
            }
        )
    }

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
                this.connect();
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainUI();
                }
            }
        }
    }
}


var playingStat = "Paused"
var radname = "Star wars Lofi"
var stat = "Connected"
var primStat = 0

fun connect(): SpotifyAppRemote{
    val clientId = "a9fa0def4cb4447293df558341852aa3";
    val redirectUrl = "https://open.spotify.com/";
    val connectionParams = ConnectionParams.Builder(clientId)
        .setRedirectUri(redirectUrl)
        .showAuthView(true)
        .build();
    println(connectionParams)
}

@Composable
fun mainUI(){
    val silkscreen_bold = FontFamily(
        Font(resId = R.font.silkscreen_bold, weight = FontWeight.Bold, style = FontStyle.Normal)
    )
    val silkscreen_reg = FontFamily(
        Font(resId = R.font.qilkscreen_regular, weight = FontWeight.Normal, style = FontStyle.Normal)
    )
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
@Composable fun body(head: FontFamily, body: FontFamily){
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
       Text(text = playingStat, fontSize = 50.sp, color = Color.White, fontFamily = font)
       Box(
           // Modifier.padding(0.dp, 150.dp, 0.dp, 0.dp)
       ) {
           OutlinedButton(
               onClick = {
                   primStat = if(primStat == 0) 1 else 0
                   playRad()
               },
               modifier = Modifier
                   .size(150.dp),
               shape = CircleShape,
               colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0XFFFFFF22))
           ) {
               Text(
                   text = if(primStat == 1) "| |" else ">",
                   color = Color.Black,
                   fontSize = 90.sp,
               )
           }
       }

   }
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
                text = "$radname",
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = font
            )
            Text(
                text = "$stat",
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