package com.bach.gardenstate.features.overview.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.overview.viewmodel.OverviewScreenViewModel
import com.bach.gardenstate.features.overview.views.GreenhouseView
import com.bach.gardenstate.features.overview.views.VegetablesView
import com.bach.gardenstate.ui.theme.GardenStateTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    overviewScreenViewModel: OverviewScreenViewModel = viewModel()
) {
    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("GardenState") }
            )
        },
    ) { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            VegetablesView(Modifier, overviewScreenViewModel)
            GreenhouseView(Modifier, overviewScreenViewModel)
        }
    }
}

@Composable
@Preview
fun OverviewScreenPreview() {
    GardenStateTheme {
        OverviewScreen()
    }
}