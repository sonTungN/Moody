package vn.edu.rmit.ui.component.select

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.materialcore.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.xml.validation.Validator


@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val startDate = dateRangePickerState.selectedStartDateMillis
                    val endDate = dateRangePickerState.selectedEndDateMillis

                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    if (startDate != null && startDate < currentTimeMillis()) {
                        errorMessage = "Start date cannot be in the past."
                    } else if (endDate != null && endDate < currentTimeMillis()) {
                        errorMessage = "End date cannot be in the past."
                    } else {
                        onDateRangeSelected(Pair(startDate, endDate))
                        onDismiss()
                    }
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Select date range"
                )
            },
            headline = {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)) {
                    Box(Modifier.weight(1f)) {
                        (if(dateRangePickerState.selectedStartDateMillis!=null) dateRangePickerState.selectedStartDateMillis?.let { getFormattedDate(it) } else "Start Date")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(1f)) {
                        (if(dateRangePickerState.selectedEndDateMillis!=null) dateRangePickerState.selectedEndDateMillis?.let { getFormattedDate(it) } else "End Date")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(0.2f)) {
                        Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = "Okk")
                    }

                }
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(12.dp)
        )
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}

fun getFormattedDate(timeInMillis: Long): String{
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yy")
    return dateFormat.format(calender.timeInMillis)
}

fun dateValidator(): (Long) -> Boolean {
    return {
            timeInMillis ->
        val endCalenderDate = Calendar.getInstance()
        endCalenderDate.timeInMillis = timeInMillis
        endCalenderDate.set(Calendar.DATE, Calendar.DATE + 20)
        timeInMillis > Calendar.getInstance().timeInMillis && timeInMillis < endCalenderDate.timeInMillis
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DateRangePickerModalPreview() {
    DateRangePickerModal(
        onDismiss = { },
        onDateRangeSelected = { dateRange ->

        }
    )
}