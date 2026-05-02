package dam.a51446.cooljetpackweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dam.a51446.cooljetpackweatherapp.R

@Composable
fun CoordinatesCard(
    lat: Float,
    lon: Float,
    onLatChange: (String) -> Unit,
    onLonChange: (String) -> Unit
) {
    var latText by remember { mutableStateOf(lat.toString()) }
    var lonText by remember { mutableStateOf(lon.toString()) }


    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.latitude_label))
            TextField(value = latText, onValueChange = {
                latText = it
                onLatChange(it)
            })

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = stringResource(R.string.longitude_label))
            TextField(value = lonText, onValueChange = {
                lonText = it
                onLonChange(it)
            })
        }
    }
}