package com.cedric.tankbalancer.ui.screen.bottomnav

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cedric.tankbalancer.R
import com.cedric.tankbalancer.ui.theme.AppTypography
import com.cedric.tankbalancer.ui.theme.largePadding
import com.cedric.tankbalancer.ui.theme.mediumPadding
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalancerScreen() {
    val currentTank = rememberSaveable { mutableStateOf(CurrentTank.NONE) }
    val currentConsumption = rememberSaveable { mutableIntStateOf(1) }
    val timePickerState = TimePickerState(
        LocalTime.now().hour,
        LocalTime.now().minute,
        true
    )

    Column {
        Row {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                AirplaneView(currentTank.value)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(.5F), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = mediumPadding)
                ) {
                    Button(onClick = { currentTank.value = CurrentTank.LEFT }) {
                        Text(text = stringResource(id = R.string.balancer_switch_to_left), color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = mediumPadding)
                ) {
                    Button(onClick = { currentTank.value = CurrentTank.RIGHT }) {
                        Text(text = stringResource(id = R.string.balancer_switch_to_left), color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(largePadding))
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = mediumPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.balancer_consumption), style = MaterialTheme.typography.headlineSmall)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = mediumPadding), horizontalArrangement = Arrangement.Center
        ) {
            SquareIconButton(
                onClick = onClick@{
                    currentConsumption.intValue -= 1
                },
                icon = painterResource(id = R.drawable.ic_minus),
                enabled = currentConsumption.intValue > 0
            )
            Spacer(modifier = Modifier.width(mediumPadding))
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .aspectRatio(1F)
                    .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(mediumPadding)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentConsumption.intValue.toString(),
                    style = AppTypography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(mediumPadding))
            SquareIconButton(onClick = { currentConsumption.intValue += 1 }, icon = painterResource(id = R.drawable.ic_plus))
        }
        Spacer(modifier = Modifier.height(largePadding))
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = mediumPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.balancer_eta), style = MaterialTheme.typography.headlineSmall)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = mediumPadding), horizontalArrangement = Arrangement.Center
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun SquareIconButton(onClick: () -> Unit, icon: Painter, enabled: Boolean = true) {
    FilledTonalButton(
        modifier = Modifier
            .size(75.dp)
            .aspectRatio(1F),
        onClick = onClick,
        shape = RoundedCornerShape(mediumPadding),
        enabled = enabled
    ) {
        Icon(painter = icon, contentDescription = null)
    }
}

@Composable
fun AirplaneView(currentTank: CurrentTank) {
    Canvas(
        modifier = Modifier
            .height(200.dp)
            .padding(5.dp)
    ) {
        val factor = 10F
        val cellPath = Path()
        val leftWingPath = Path()
        val rightWingPath = Path()
        val propellerPath = Path()
        val canvasWidth = size.width
        val canvasHeight = size.height
        val middleX = canvasWidth / 2F
        propellerPath.moveTo(middleX - 7 * factor, 0F)
        propellerPath.lineTo(middleX + 7 * factor, 0F)
        cellPath.moveTo(middleX, 0F)
        cellPath.arcTo(
            Rect(middleX - 5 * factor, 10F, middleX + 5 * factor, 10 * factor),
            180F,
            180F,
            true
        )
        cellPath.lineTo(middleX + 5 * factor, 20 * factor)
        cellPath.moveTo(middleX + 5 * factor, 35 * factor)
        cellPath.lineTo(middleX + 5 * factor, 45 * factor)
        cellPath.lineTo(middleX + 15 * factor, 50 * factor)
        cellPath.lineTo(middleX + 15 * factor, 55 * factor)
        cellPath.lineTo(middleX, 52 * factor)
        cellPath.lineTo(middleX - 15 * factor, 55 * factor)
        cellPath.lineTo(middleX - 15 * factor, 50 * factor)
        cellPath.lineTo(middleX - 5 * factor, 45 * factor)
        cellPath.lineTo(middleX - 5 * factor, 35 * factor)
        // Begin left wing
        // End left wing
        cellPath.moveTo(middleX - 5 * factor, 20 * factor)
        cellPath.lineTo(middleX - 5 * factor, 5 * factor)

        rightWingPath.moveTo(middleX + 5 * factor, 20 * factor)
        rightWingPath.lineTo(canvasWidth, 25 * factor)
        rightWingPath.lineTo(canvasWidth, 30 * factor)
        rightWingPath.lineTo(middleX + 5 * factor, 35 * factor)
        rightWingPath.close()

        leftWingPath.moveTo(middleX - 5 * factor, 35 * factor)
        leftWingPath.lineTo(0F, 30 * factor)
        leftWingPath.lineTo(0F, 25 * factor)
        leftWingPath.lineTo(middleX - 5 * factor, 20 * factor)
        leftWingPath.close()

        drawPath(cellPath, color = Color.Black, style = Stroke(width = 10F))
        drawPath(propellerPath, color = Color.Black, style = Stroke(width = 10F))
        drawPath(leftWingPath, color = if (currentTank == CurrentTank.LEFT) Color.Green else Color.Red, style = Stroke(width = 10F))
        drawPath(rightWingPath, color = if (currentTank == CurrentTank.RIGHT) Color.Green else Color.Red, style = Stroke(width = 10F))
    }
}

enum class CurrentTank {
    LEFT,
    RIGHT,
    NONE
}