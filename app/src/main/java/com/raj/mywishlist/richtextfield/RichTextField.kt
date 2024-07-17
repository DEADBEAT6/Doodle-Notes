package com.raj.myapplication.MainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import com.raj.mywishlist.WishViewModel

//import com.stevdza.san.comptest.components.LinkDialog

/**
Be sure that you have those two dependencies:
// Rich Text Editor
implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-beta03")
// Extension Icons
implementation("androidx.compose.material:material-icons-extended:1.5.3")
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RichTextInput(viewModel: WishViewModel, state: RichTextState, isDrawingMode: Boolean,id:Long) {

    val titleSize = MaterialTheme.typography.displaySmall.fontSize
    val subtitleSize = MaterialTheme.typography.titleLarge.fontSize
    LaunchedEffect(viewModel.wishDescriptionState) {
        if (viewModel.wishDescriptionState.isNotBlank()) {
            state.setHtml(viewModel.wishDescriptionState)
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditorControls(
            state = state,
            onBoldClick = {
                state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
            },
            onItalicClick = {
                state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
            },
            onUnderlineClick = {
                state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
            },
            onTitleClick = {
                state.toggleSpanStyle(SpanStyle(fontSize = titleSize))
            },
            onSubtitleClick = {
                state.toggleSpanStyle(SpanStyle(fontSize = subtitleSize))
            },
            onTextColorClick = {
                state.toggleSpanStyle(SpanStyle(color = Color.Red))
            },
            onStartAlignClick = {
                state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
            },
            onEndAlignClick = {
                state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
            },
            onCenterAlignClick = {
                state.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
            },

            )


        RichTextEditor(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = state,
            colors = RichTextEditorDefaults.richTextEditorColors(containerColor = Color.Transparent),
            readOnly = !isDrawingMode,

        )
    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditorControls(
    modifier: Modifier = Modifier,
    state: RichTextState,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSubtitleClick: () -> Unit,
    onTextColorClick: () -> Unit,
    onStartAlignClick: () -> Unit,
    onEndAlignClick: () -> Unit,
    onCenterAlignClick: () -> Unit,

    ) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var subtitleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorSelected by rememberSaveable { mutableStateOf(false) }
    var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }

    FlowRow(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(0.5.dp),
    ) {
        ControlWrapper(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatBold,
                contentDescription = "Bold Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatItalic,
                contentDescription = "Italic Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatUnderlined,
                contentDescription = "Underline Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = titleSelected,
            onChangeClick = { titleSelected = it },
            onClick = onTitleClick
        ) {
            Icon(
                imageVector = Icons.Default.Title,
                contentDescription = "Title Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = subtitleSelected,
            onChangeClick = { subtitleSelected = it },
            onClick = onSubtitleClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatSize,
                contentDescription = "Subtitle Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = textColorSelected,
            onChangeClick = { textColorSelected = it },
            onClick = onTextColorClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatColorText,
                contentDescription = "Text Color Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 0,
            onChangeClick = { alignmentSelected = 0 },
            onClick = onStartAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignLeft,
                contentDescription = "Start Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 1,
            onChangeClick = { alignmentSelected = 1 },
            onClick = onCenterAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignCenter,
                contentDescription = "Center Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 2,
            onChangeClick = { alignmentSelected = 2 },
            onClick = onEndAlignClick,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.FormatAlignRight,
                contentDescription = "End Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (selected) selectedColor
                else unselectedColor
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}