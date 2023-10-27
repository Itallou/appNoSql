package com.example.appnosql

import DBHandler
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appnosql.model.CourseModel
import com.example.appnosql.ui.theme.AppNoSqlTheme

class ViewCourses : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNoSqlTheme {
                // on below line we are specifying background color for our application
                Surface(
                    // on below line we are specifying modifier and color for our app
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    // on the below line we are specifying the theme as the scaffold.
                    Scaffold(
                        // in scaffold we are specifying the top bar.
                        topBar = {
                            // inside top bar we are specifying background color.
                            TopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                                // along with that we are specifying title for our top bar.
                                title = {
                                    // in the top bar we are specifying tile as a text
                                    Text(
                                        // on below line we are specifying
                                        // text to display in top app bar.
                                        text = "GFG",

                                        // on below line we are specifying
                                        // modifier to fill max width.
                                        modifier = Modifier.fillMaxWidth(),

                                        // on below line we are specifying
                                        // text alignment.
                                        textAlign = TextAlign.Center,

                                        // on below line we are specifying
                                        // color for our text.
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    ) {
                        readDataFromDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun readDataFromDatabase(context: Context) {
    val dbHandler: DBHandler = DBHandler(context)
    var courseList by remember { mutableStateOf(listOf<CourseModel>()) }

    courseList = dbHandler.readCourses() ?: emptyList()

    // on below line we are creating a lazy column for displaying a list view.
    LazyColumn {
        // on below line we are setting data for each item of our listview.
        item {
            Spacer(modifier = Modifier.height(56.dp))
        }
        itemsIndexed(courseList) { index, course ->
            // on below line we are creating a card for our list view item.
            Card(
                // on below line we are adding padding from our all sides.
                modifier = Modifier.padding(8.dp)
            ) {
                // on below line we are creating a row for our list view item.
                Column(
                    // for our row we are adding modifier to set padding from all sides.
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = course.courseName,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    // on below line inside row we are adding spacer
                    Spacer(modifier = Modifier.width(5.dp))

                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = "Course Tracks : " + course.courseTracks,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    // on below line inside row we are adding spacer
                    Spacer(modifier = Modifier.width(5.dp))

                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = "Course Duration : " + course.courseDuration,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )
                    // on below line inside row we are adding spacer
                    Spacer(modifier = Modifier.width(5.dp))

                    // on the below line we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = "Description : " + course.courseDescription,

                        // on below line we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // on below line we are adding color for our text
                        color = Color.Black, textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(
                            onClick = {
                                val dbHandler = DBHandler(context)
                                val success = dbHandler.deleteCourse(courseList[index].courseName)
                                if (success){
                                    // Curso excluído
                                }
                            }
                        ){
                            Text("Delete")
                        }

                        Button(
                            onClick = {
                                val intent = Intent(context, UpdateCourses::class.java)
                                intent.putExtra("courseName", courseList[index].courseName)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(8.dp)
                        ){
                            Text("Update")
                        }
                    }
                }
            }
        }
    }
}