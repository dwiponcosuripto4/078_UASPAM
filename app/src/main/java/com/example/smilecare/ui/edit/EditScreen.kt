package com.example.smilecare.ui.edit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smilecare.navigation.DestinasiNavigasi
import com.example.smilecare.ui.BookingTopAppBar
import com.example.smilecare.ui.PenyediaViewModel
import com.example.smilecare.ui.add.AddBookingBody
import kotlinx.coroutines.launch

object EditDestination : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes ="Edit Booking"
    const val bookingId = "itemId"
    val routeWithArgs = "${EditDestination.route}/{$bookingId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            BookingTopAppBar(
                title =EditDestination.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        AddBookingBody(
            uiStateBooking = viewModel.bookingUiState,
            onBookingValueChange = viewModel::updateUIState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateBooking()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}