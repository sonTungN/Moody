package vn.edu.rmit.ui.screen.manager.properties

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.edu.rmit.data.service.PropertyService
import javax.inject.Inject

@HiltViewModel
class ManagerPropertiesViewModel @Inject constructor(
    private val propertyService: PropertyService
) : ViewModel() {
    var properties = propertyService.getProperties()
}