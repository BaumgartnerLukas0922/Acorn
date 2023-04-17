package at.htl.acornapp.data.TopSheet

import androidx.lifecycle.LiveData
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Sheet.SheetDao
import at.htl.acornapp.data.Sheet.SheetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopSheetRepository(private val topSheetDao: TopSheetDao) {

    val allTopSheets: LiveData<List<TopSheet>> = topSheetDao.readAllData()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addSheet(newSheet: Sheet){
        coroutineScope.launch(Dispatchers.IO){
            topSheetDao.addTopSheet(
                TopSheet(
                    sheet = newSheet,
                    tsLong = System.currentTimeMillis()
                )
            )
            deleteOldest()
        }
    }

    fun deleteOldest(){
        coroutineScope.launch(Dispatchers.IO){
            topSheetDao.deleteOldest()
        }
    }

    fun deleteAll(){
        coroutineScope.launch(Dispatchers.IO){
            topSheetDao.drop()
        }
    }
}