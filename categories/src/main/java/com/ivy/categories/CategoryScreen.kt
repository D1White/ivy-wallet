package com.ivy.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ivy.categories.component.categoriesList
import com.ivy.categories.data.CategoryListItemUi
import com.ivy.categories.data.CategoryListItemUi.ParentCategory
import com.ivy.core.domain.pure.format.dummyValueUi
import com.ivy.core.ui.components.ScreenBottomBar
import com.ivy.core.ui.data.dummyCategoryUi
import com.ivy.core.ui.data.period.SelectedPeriodUi
import com.ivy.core.ui.data.period.dummyRangeUi
import com.ivy.core.ui.time.PeriodButton
import com.ivy.core.ui.time.PeriodModal
import com.ivy.design.l0_system.color.Blue
import com.ivy.design.l0_system.color.Green
import com.ivy.design.l0_system.color.Purple2Dark
import com.ivy.design.l0_system.color.Red
import com.ivy.design.l1_buildingBlocks.SpacerVer
import com.ivy.design.l1_buildingBlocks.SpacerWeight
import com.ivy.design.l2_components.modal.IvyModal
import com.ivy.design.l2_components.modal.rememberIvyModal
import com.ivy.design.l3_ivyComponents.Feeling
import com.ivy.design.l3_ivyComponents.ReorderButton
import com.ivy.design.l3_ivyComponents.Visibility
import com.ivy.design.l3_ivyComponents.button.ButtonSize
import com.ivy.design.l3_ivyComponents.button.IvyButton
import com.ivy.design.util.IvyPreview
import com.ivy.resources.R

@Composable
fun BoxScope.CategoryScreen() {
    val viewModel: CategoryViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsState()
}

@Composable
private fun BoxScope.UI(
    state: CategoryState,
    onEvent: (CategoryEvent) -> Unit,
) {
    val periodModal = rememberIvyModal()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        item(key = "header") {
            SpacerVer(height = 16.dp)
            Header(
                selectedPeriodUi = state.selectedPeriod,
                periodModal = periodModal,
                onReorder = {
                    // TODO: Implement
                }
            )
            SpacerVer(height = 20.dp)
        }
        categoriesList(
            items = state.items,
            emptyState = state.emptyState,
            onCategoryClick = {
                // TODO: Implement
            },
            onParentCategoryClick = {
                // TODO: Implement
            },
            onCreateCategory = {
                // TODO: Implement
            }
        )
        item(key = "last_item_spacer") {
            SpacerVer(height = 64.dp)
        }
    }

    ScreenBottomBar {
        IvyButton(
            size = ButtonSize.Small,
            visibility = Visibility.High,
            feeling = Feeling.Positive,
            text = "New category",
            icon = R.drawable.ic_round_add_24
        ) {
            // TODO: Implement
        }
    }

    state.selectedPeriod?.let {
        PeriodModal(
            modal = periodModal,
            selectedPeriod = state.selectedPeriod
        )
    }

}

@Composable
private fun Header(
    selectedPeriodUi: SelectedPeriodUi?,
    periodModal: IvyModal,
    onReorder: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selectedPeriodUi != null) {
            PeriodButton(selectedPeriod = selectedPeriodUi, periodModal = periodModal)
        }
        SpacerWeight(weight = 1f)
        ReorderButton(onClick = onReorder)
    }
}


// region Preview
@Preview
@Composable
private fun Preview_Empty() {
    IvyPreview {
        UI(
            state = CategoryState(
                selectedPeriod = SelectedPeriodUi.AllTime(
                    btnText = "All-time",
                    rangeUi = dummyRangeUi()
                ),
                items = emptyList(),
                emptyState = true
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun Preview() {
    IvyPreview {
        UI(
            state = CategoryState(
                selectedPeriod = SelectedPeriodUi.AllTime(
                    btnText = "Sep",
                    rangeUi = dummyRangeUi()
                ),
                items = listOf(
                    ParentCategory(
                        parentCategory = dummyCategoryUi("Business"),
                        balance = dummyValueUi("+3,320.50"),
                        categoryCards = listOf(
                            CategoryListItemUi.CategoryCard(
                                category = dummyCategoryUi("Category 1"),
                                balance = dummyValueUi("-1,000.00"),
                            ),
                            CategoryListItemUi.CategoryCard(
                                category = dummyCategoryUi("Category 2", color = Blue),
                                balance = dummyValueUi("0.00"),
                            ),
                            CategoryListItemUi.CategoryCard(
                                category = dummyCategoryUi("Category 3", color = Red),
                                balance = dummyValueUi("+4,320.50"),
                            ),
                        ),
                        categoriesCount = 3,
                    ),
                    CategoryListItemUi.CategoryCard(
                        category = dummyCategoryUi("Tech", color = Purple2Dark),
                        balance = dummyValueUi("-30k"),
                    ),
                    CategoryListItemUi.CategoryCard(
                        category = dummyCategoryUi("Groceries", color = Green),
                        balance = dummyValueUi("-5,025.54"),
                    ),
                    CategoryListItemUi.Archived(
                        categoryCards = listOf(
                            CategoryListItemUi.CategoryCard(
                                category = dummyCategoryUi("Category 1"),
                                balance = dummyValueUi("-1,000.00", "BGN"),
                            ),
                            CategoryListItemUi.CategoryCard(
                                category = dummyCategoryUi("Category 2", color = Blue),
                                balance = dummyValueUi("0.00"),
                            ),
                            CategoryListItemUi.CategoryCard(
                                category = dummyCategoryUi("Account 3", color = Red),
                                balance = dummyValueUi("+4,320.50"),
                            ),
                        ),
                        count = 3,
                    )
                ),
                emptyState = false,
            ),
            onEvent = {}
        )
    }
}
// endregion