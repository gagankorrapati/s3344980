package uk.ac.tees.mad.univid.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uk.ac.tees.mad.univid.model.PetItem

@Composable
fun DetailScreen(petItems: PetItem) {
    Text(text = petItems.name)
}