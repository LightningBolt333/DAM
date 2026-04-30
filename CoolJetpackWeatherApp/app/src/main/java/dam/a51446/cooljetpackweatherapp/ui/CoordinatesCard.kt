package dam.a51446.cooljetpackweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
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
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.latitude_label))
            TextField(value = lat.toString(), onValueChange = onLatChange)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = stringResource(R.string.longitude_label))
            TextField(value = lon.toString(), onValueChange = onLonChange)
        }
    }
}