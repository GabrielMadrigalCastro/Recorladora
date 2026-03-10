package com.example.recorladora.viewmodel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recorladora.repository.AppContainer
import com.example.recorladora.viewmodel.detail.FormulaDetailScreen
import com.example.recorladora.viewmodel.edit.FormulaEditScreen
import com.example.recorladora.viewmodel.list.FormulaListScreen

@Composable
fun AppNavGraph(container: AppContainer) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            FormulaListScreen(
                container = container,
                onAdd = { nav.navigate(Routes.edit(null)) },
                onOpen = { id -> nav.navigate(Routes.detail(id)) },
                onEdit = { id -> nav.navigate(Routes.edit(id)) })
        }

        composable(
            route = Routes.DETAIL, arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) {
            val id = it.arguments?.getLong("id") ?: return@composable

            FormulaDetailScreen(
                container = container,
                id = id,
                onBack = { nav.popBackStack() },
                onEdit = { nav.navigate(Routes.edit(id)) })
        }

        composable(
            route = Routes.EDIT, arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = -1L
            })
        ) {
            val raw = it.arguments?.getLong("id") ?: -1L
            val id: Long? = raw.takeIf { v -> v > 0 }

            FormulaEditScreen(
                container = container, id = id, onDone = { nav.popBackStack() })
        }
    }
}