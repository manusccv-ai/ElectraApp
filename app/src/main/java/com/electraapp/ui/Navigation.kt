package com.electraapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.electraapp.data.database.AppDatabase
import com.electraapp.data.repository.SubscriberRepository
import com.electraapp.ui.screens.AddEditSubscriberScreen
import com.electraapp.ui.screens.SettingsScreen
import com.electraapp.ui.screens.SubscriberDetailScreen
import com.electraapp.ui.screens.SubscriberListScreen
import com.electraapp.viewmodel.ElectraViewModel
import com.electraapp.viewmodel.ElectraViewModelFactory
import androidx.compose.ui.platform.LocalContext

object Destinations {
    const val SUBSCRIBER_LIST_ROUTE = "subscriber_list"
    const val SUBSCRIBER_DETAIL_ROUTE = "subscriber_detail/{subscriberId}"
    const val ADD_EDIT_SUBSCRIBER_ROUTE = "add_edit_subscriber/{subscriberId}"
    const val SETTINGS_ROUTE = "settings"
}

@Composable
fun ElectraAppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = SubscriberRepository(database.subscriberDao())
    val electraViewModel: ElectraViewModel = viewModel(factory = ElectraViewModelFactory(repository))

    NavHost(navController = navController, startDestination = Destinations.SUBSCRIBER_LIST_ROUTE) {
        composable(Destinations.SUBSCRIBER_LIST_ROUTE) {
            val subscribers by electraViewModel.allSubscribers.collectAsState(initial = emptyList())
            SubscriberListScreen(
                subscribers = subscribers,
                onAddSubscriberClick = { navController.navigate(Destinations.ADD_EDIT_SUBSCRIBER_ROUTE.replace("/{subscriberId}", "")) },
                onSubscriberClick = { subscriber -> navController.navigate("subscriber_detail/${subscriber.id}") }
            )
        }
        composable(
            Destinations.SUBSCRIBER_DETAIL_ROUTE,
            arguments = listOf(navArgument("subscriberId") { type = NavType.IntType })
        ) {
            val subscriberId = it.arguments?.getInt("subscriberId")
            LaunchedEffect(subscriberId) {
                electraViewModel.selectSubscriber(subscriberId)
            }
            val selectedSubscriber by electraViewModel.selectedSubscriber.collectAsState()
            val monthlyAmperePrice by electraViewModel.monthlyAmperePrice.collectAsState()

            SubscriberDetailScreen(
                subscriber = selectedSubscriber,
                onBackClick = { navController.popBackStack() },
                onEditClick = { subscriber -> navController.navigate("add_edit_subscriber/${subscriber.id}") },
                onDeleteClick = { subscriber ->
                    electraViewModel.deleteSubscriber(subscriber)
                    navController.popBackStack()
                },
                onTogglePaidStatus = { subscriber -> electraViewModel.togglePaidStatus(subscriber) },
                monthlyAmperePrice = monthlyAmperePrice
            )
        }
        composable(
            Destinations.ADD_EDIT_SUBSCRIBER_ROUTE,
            arguments = listOf(navArgument("subscriberId") { type = NavType.IntType; defaultValue = -1 })
        ) {
            val subscriberId = it.arguments?.getInt("subscriberId")
            LaunchedEffect(subscriberId) {
                electraViewModel.selectSubscriber(subscriberId.takeIf { it != -1 })
            }
            val selectedSubscriber by electraViewModel.selectedSubscriber.collectAsState()

            AddEditSubscriberScreen(
                subscriber = selectedSubscriber,
                onSaveClick = {
                    electraViewModel.insertSubscriber(it)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Destinations.SETTINGS_ROUTE) {
            val monthlyAmperePrice by electraViewModel.monthlyAmperePrice.collectAsState()
            SettingsScreen(
                currentAmperePrice = monthlyAmperePrice,
                onSavePrice = { price ->
                    electraViewModel.setMonthlyAmperePrice(price)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}


