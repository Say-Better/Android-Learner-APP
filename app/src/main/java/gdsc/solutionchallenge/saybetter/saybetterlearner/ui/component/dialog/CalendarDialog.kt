package gdsc.solutionchallenge.saybetter.saybetterlearner.ui.component.dialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import gdsc.solutionchallenge.saybetter.saybetterlearner.R
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.Black
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.MainGreen
import gdsc.solutionchallenge.saybetter.saybetterlearner.ui.theme.White
import gdsc.solutionchallenge.saybetter.saybetterlearner.utils.customclick.clickWithScaleAnimation
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class CalendarDialog {
    @Composable
    fun CalendarDialogScreen(
        onClickCancel: () -> Unit,
        calendarCallback: CalendarCallback
    ) {
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
        val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
        val daysOfWeek = remember { daysOfWeek() }
        val coroutineScope = rememberCoroutineScope()


        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first()
        )

        Dialog(
            onDismissRequest = { onClickCancel() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            Box(
                modifier = Modifier
                    .width(520.dp)
                    .background(White, RoundedCornerShape(20.dp))// Card의 모든 꼭지점에 8.dp의 둥근 모서리 적용
            )
            {
                Column (modifier = Modifier.padding(20.dp)){
                    CalendarDateBar(state.firstVisibleMonth.yearMonth,
                        goBack = {
                            coroutineScope.launch {
                                state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                            }
                        },
                        goForward = {
                            coroutineScope.launch {
                                state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                            }
                        })
                    DaysOfWeekTitle(daysOfWeek = daysOfWeek())
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        drawLine(
                            color = MainGreen,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 3f
                        )
                    }
                    CalendarView(state, onDateClick = {day->
                        calendarCallback.onDateClickEvent(day.date.toString())
                        onClickCancel()
                    })
                }
            }
        }
    }
    @Composable
    fun CalendarView(state : CalendarState, onDateClick: (CalendarDay) -> Unit) {

        HorizontalCalendar(
            state = state,
            dayContent = { CalendarDay(it,
                onDateClick = {day->
                    onDateClick(day)
                }) }
        )
    }

    @Composable
    fun CalendarDay(day: CalendarDay, onDateClick: (CalendarDay) -> Unit) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clickWithScaleAnimation({onDateClick(day)}),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material.Text(text = day.date.dayOfMonth.toString(),
                color = Black)
        }
    }
    @Composable
    fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    color = MainGreen,
                    fontWeight = FontWeight.W700,
                )
            }
        }
    }

    @Composable
    fun CalendarDateBar(
        yearmonth : YearMonth,
        goBack: () -> Unit,
        goForward:()->Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = yearmonth.toString().replace('-', '.'),
                fontSize = 30.sp,
                color = MainGreen,
                fontWeight = FontWeight.W800,
                modifier = Modifier.weight(1f)
            )

            Row (){
                Image(painter = painterResource(id = R.drawable.ic_calendar_left),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickWithScaleAnimation(goBack))
                Spacer(modifier = Modifier.width(20.dp))
                Image(painter = painterResource(id = R.drawable.ic_calendar_right),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickWithScaleAnimation(goForward))
            }
        }
    }
}

interface CalendarCallback {
    fun onDateClickEvent(date : String)
}