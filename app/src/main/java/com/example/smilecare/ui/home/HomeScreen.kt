package com.example.smilecare.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smilecare.R
import com.example.smilecare.model.Booking
import com.example.smilecare.navigation.DestinasiNavigasi
import com.example.smilecare.ui.BookingTopAppBar
import com.example.smilecare.ui.PenyediaViewModel
import com.example.smilecare.ui.theme.SmileCareTheme

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Reservasi Anda"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookingTopAppBar(
                title = "Reservasi Anda",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        },
    ) { innerPadding ->
        val uiStateBooking by viewModel.homeUIState.collectAsState()
        BodyHome(
            itemBooking = uiStateBooking.listBooking,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onBookingClick = onDetailClick,
            onSearchQueryChanged = { query: String ->
                viewModel.setSearchQuery(query)
            }
        )
    }
}

@Composable
fun BodyHome(
    itemBooking: List<Booking>,
    modifier: Modifier = Modifier,
    onBookingClick: (String) -> Unit = {},
    onSearchQueryChanged: (String) -> Unit,
    onSearchClear: () -> Unit ={}
) {
    var searchQuery by remember { mutableStateOf("") }
    Column(
        modifier = modifier
    ) {
        SearchBar(
            searchQuery = searchQuery,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            onSearchQueryChanged = {
                searchQuery = it
                onSearchQueryChanged(it)
            },
            onSearchClear = {
                searchQuery = ""
                onSearchClear()
            }
        )
        Text(
            text = "Klinik SmileCare dr. Riayani",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)

        )
        Divider(modifier = Modifier.fillMaxWidth())
        if (itemBooking.isEmpty()) {
            Text(
                text = "Tidak ada data Booking",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        } else {
            val filteredList = itemBooking.filter { booking ->
                booking.jenisPerawatan.contains(searchQuery, ignoreCase = true) ||
                        booking.tanggal.contains(searchQuery, ignoreCase = true) ||
                        booking.status.contains(searchQuery, ignoreCase = true)
            }

            if (filteredList.isEmpty() && searchQuery.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.search),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                ListBooking(
                    itemBooking = filteredList,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                    onItemClick = { onBookingClick(it.id) }
                )
            }
        }
    }
}

@Composable
fun ListBooking(
    itemBooking: List<Booking>,
    modifier: Modifier = Modifier,
    onItemClick: (Booking) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        this.items(itemBooking, key = { it.id }) { booking ->
            DataBooking(
                booking = booking,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(booking) }
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun DataBooking(
    booking: Booking,
    modifier: Modifier = Modifier
) {
    val textColor = when (booking.status) {
        "Pending" -> MaterialTheme.colorScheme.error
        "Dikonfirmasi" -> MaterialTheme.colorScheme.primary
        "Selesai" -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = booking.jenisPerawatan,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Text(
                    text = booking.tanggal,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = booking.waktu,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = booking.status,
                style = MaterialTheme.typography.titleMedium,
                color = textColor
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClear: () -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChanged(it) },
        placeholder = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                Text(text = stringResource(R.string.search))
            }
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchClear() }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        },
        modifier = modifier
    )
}

