package uy.com.temperoni.recipes.commons

import org.junit.Rule
import uy.com.temperoni.recipes.rule.MainDispatcherRule
import uy.com.temperoni.recipes.ui.model.Recipe

abstract class BaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
}