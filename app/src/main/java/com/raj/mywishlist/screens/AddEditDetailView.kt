
package com.raj.mywishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raj.mywishlist.data.Wish
import com.raj.mywishlist.screens.components.AppBarView
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){

    val snackMessage = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val isButtonClicked = remember { mutableStateOf(false) }
    val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L,"","", null))
    if (id != 0L){
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
        viewModel.wishDrawingState = wish.value.drawing
        viewModel.wishdrawlist = viewModel.wishDrawingState?.let { convertByteArrayToList(it) } ?: emptyList()

    }

    Scaffold(
        scaffoldState =scaffoldState,
        topBar = {

            AppBarView(
                title = if(id!= 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish),

                onBackNavClicked = {navController.navigateUp()})
        }
    ){
        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(
                label = "Title",
                value = viewModel.wishTitleState ,
                onValueChanged = {
                    viewModel.onWishTitleChange(it)
                }
            )

            Drawing(viewModel = viewModel, modifier = Modifier.fillMaxSize().weight(1f))

            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { /*TODO*/ if(!isButtonClicked.value){
                isButtonClicked.value = true
                if(viewModel.wishTitleState.isNotEmpty() ){
                    if(id!=0L){
                        viewModel.updateWish(
                            Wish(
                                id= id ,
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim(),
                                drawing = viewModel.wishDrawingState)
                        )
                        snackMessage.value = "Wish Has been Updated Complete!! "

                    }else{
                        viewModel.addWish(
                            Wish(title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim(),
                                drawing = viewModel.wishDrawingState)
                        )
                        snackMessage.value = "Wish Has been Created !! "
                    }
                    //TODO updata and add wish


                }else{
                    snackMessage.value= "Enter fields to create a wish"

                }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value )
                    navController.navigateUp()
                }
            }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if(id!= 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label : String,
    value: String,
    onValueChanged : (String) ->Unit
){
    OutlinedTextField(
        value = value ,
        onValueChange = onValueChanged,
        label = {Text(text = label,color= Color.Black)},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource (id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )

    )
}

@Preview
@Composable
fun WishTextFieldPrev(){
    WishTextField(label = "label", value = " text", onValueChanged = {})

}
