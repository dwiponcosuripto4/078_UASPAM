package com.example.smilecare.ui.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smilecare.R
import com.example.smilecare.model.SumberData
import com.example.smilecare.navigation.DestinasiNavigasi
import com.example.smilecare.ui.BookingTopAppBar
import com.example.smilecare.ui.DetailBooking
import com.example.smilecare.ui.PenyediaViewModel
import com.example.smilecare.ui.UIStateBooking
import com.google.android.libraries.places.api.model.LocalDate
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DestinasiBooking : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(color = Color(0xFF00A79D)),
        topBar = {
            BookingTopAppBar(
                title = DestinasiBooking.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->

        AddBookingBody(
            uiStateBooking = viewModel.uiStateBooking,
            onBookingValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveBooking()
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

@Composable
fun AddBookingBody(
    uiStateBooking: UIStateBooking,
    onBookingValueChange: (DetailBooking) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        FormInput(
            detailBooking = uiStateBooking.detailBooking,
            onValueChange = onBookingValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                onBookingValueChange(uiStateBooking.detailBooking.copy(tanggal = currentDate.toString()))
                onSaveClick()
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_submit))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    detailBooking: DetailBooking,
    modifier: Modifier = Modifier,
    onValueChange: (DetailBooking) -> Unit = {},
    enabled: Boolean = true
) {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Other fields...
        OutlinedTextField(
            value = detailBooking.nama,
            onValueChange = { onValueChange(detailBooking.copy(nama = it)) },
            label = { Text(stringResource(R.string.nama)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailBooking.alamat,
            onValueChange = { onValueChange(detailBooking.copy(alamat = it)) },
            label = { Text(stringResource(R.string.alamat)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
        OutlinedTextField(
            value = detailBooking.telpon,
            onValueChange = { onValueChange(detailBooking.copy(telpon = it)) },
            label = { Text(stringResource(R.string.telpon)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        // Waktu (dropdown)
        RadioButtonGroup(
            label = stringResource(R.string.pilih_waktu),
            options = SumberData.waktu,
            selectedOption = detailBooking.waktu,
            onOptionSelected = { onValueChange(detailBooking.copy(waktu = it)) },
            enabled = enabled
        )

        // Jenis Perawatan (dropdown)
        RadioButtonGroup(
            label = stringResource(R.string.pilih_jenis),
            options = SumberData.jenis,
            selectedOption = detailBooking.jenisPerawatan,
            onOptionSelected = { onValueChange(detailBooking.copy(jenisPerawatan = it)) },
            enabled = enabled
        )

        // Catatan Khusus
        OutlinedTextField(
            value = detailBooking.catatanKhusus,
            onValueChange = { onValueChange(detailBooking.copy(catatanKhusus = it)) },
            label = { Text(stringResource(R.string.catatanKhusus)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )

        // Status
        OutlinedTextField(
            value = detailBooking.status,
            onValueChange = { onValueChange(detailBooking.copy(status = it)) },
            label = { Text(stringResource(R.string.status)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )
        OutlinedTextField(
            value = detailBooking.nomorAntrian,
            onValueChange = { onValueChange(detailBooking.copy(nomorAntrian = it)) },
            label = { Text(stringResource(R.string.nomorAntrian)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true
        )
        OutlinedTextField(
            value = currentDate.toString(),
            onValueChange = { onValueChange(detailBooking.copy(tanggal = it)) },
            label = { Text(stringResource(R.string.tanggal)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,  // Tanggal tidak dapat diubah
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioButtonGroup(
    label: String,
    options: List<Int>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    enabled: Boolean
) {
    Text(label)
    options.forEach { optionId ->
        val optionLabel = stringResource(optionId)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RadioButton(
                selected = selectedOption == optionLabel,
                onClick = { onOptionSelected(optionLabel) },
                enabled = enabled
            )
            Text(text = optionLabel)
        }
    }
}
