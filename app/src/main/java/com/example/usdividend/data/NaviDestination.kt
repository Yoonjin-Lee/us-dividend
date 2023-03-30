package com.example.usdividend.data

import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.res.vectorResource
import com.example.usdividend.DividendScreen
import com.example.usdividend.R

data class NaviDestination(
    val icon : ImageVector,
    val name : String,
    val route : String,
//    val screen : @Composable () -> Unit
)