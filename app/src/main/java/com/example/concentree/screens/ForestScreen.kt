package com.example.concentree.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concentree.R
import com.example.concentree.roomDB.ForestTree
import com.example.concentree.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.format.DateTimeFormatter
import kotlin.math.pow
import kotlin.math.roundToInt

data class Tile(val x: Int, val y: Int, val resourceId: Int)

private const val TILE_WIDTH = 100
private const val TILE_HEIGHT = 60
private const val MAX_FOREST_INDEX = 9
@Composable
fun ForestScreen(viewModel: AppViewModel) {

    var selectedTree by remember { mutableStateOf<ForestTree?>(null) }
    var isDragging by remember { mutableStateOf(false) }
    var forestIndex by remember { mutableIntStateOf(0) }

    val tiles = remember {
        (0..4).flatMap { y ->
            (0..4).map { x ->
                Tile(
                    x,
                    y,
                    R.drawable.isometric_0000
                )
            }
        }
    }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }


    val trees by viewModel.forestTreeList.collectAsState(initial = emptyList())
    // Fetch initial data
    LaunchedEffect(Unit) {
        viewModel.getAllTreesInForest()
        viewModel.getUserById(1)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                ) {
                    selectedTree = null
                }
            }
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))

            ForestNavigation(forestIndex = forestIndex, onIndexChange = { it ->
                forestIndex = it
                selectedTree = null
                isDragging = false
            })

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeightPx / 8).dp)
            ) {
                IsometricTilemap(tiles, screenWidthPx / 2)
                trees.filter { tree -> tree.forestId == forestIndex && tree.onForest }
                    .sortedBy { tree -> tree.xPosition + tree.yPosition }
                    .forEach { tree ->
                        Plant(
                            forestTree = tree,
                            isDragging = (selectedTree?.id == tree.id) && isDragging,
                            middle = screenWidthPx / 2,
                            onUpdateTree = { x, y ->
                                viewModel.UpdateForestTree(tree.copy(xPosition = x, yPosition = y))
                                isDragging = false
                                selectedTree = null
                            },
                            onSelectTree = {
                                selectedTree = it
                                isDragging = false
                            },
                            isEmptySpace = { x, y->
                                trees.none { it.xPosition == x && it.yPosition == y && it.forestId == forestIndex && it.onForest }
                            }
                        )
                    }
            }

            selectedTree?.let {
                ViewSelected(it)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                selectedTree?.let {
                    FloatingButton(
                        isDragging = isDragging,
                        onMoveToggle = {
                            isDragging = true
                        },
                        onDelete = {
                            val updateTree = selectedTree!!.copy(onForest = false)
                            viewModel.UpdateForestTree(updateTree)
                            selectedTree = null
                        }
                    )
                }
            }

            AvailableTreesGrid(trees = trees, forestIndex = forestIndex, viewModel = viewModel, onTreeSelected = { selectedTree = it })
        }
    }
}

@Composable
fun Plant(
    forestTree: ForestTree,
    isDragging: Boolean,
    middle: Float,
    onUpdateTree: (Int, Int) -> Unit,
    onSelectTree: (ForestTree) -> Unit,
    isEmptySpace : (Int,Int) -> Boolean
) {

    var offsetX by remember { mutableFloatStateOf(((forestTree.xPosition - forestTree.yPosition) * TILE_WIDTH) + (middle - TILE_WIDTH)) }
    var offsetY by remember { mutableFloatStateOf((((forestTree.xPosition + forestTree.yPosition) * TILE_HEIGHT) - TILE_HEIGHT * 3).toFloat()) }
    var prevX by remember { mutableFloatStateOf(offsetX) }
    var prevY by remember { mutableFloatStateOf(offsetY) }

    LaunchedEffect(forestTree.xPosition, forestTree.yPosition) {
        offsetX = ((forestTree.xPosition - forestTree.yPosition) * TILE_WIDTH) + (middle - TILE_WIDTH)
        offsetY = ((forestTree.xPosition + forestTree.yPosition) * TILE_HEIGHT - TILE_HEIGHT * 3).toFloat()
        prevX = offsetX
        prevY = offsetY
    }

    Box(
        modifier = Modifier
            .size(80.dp, 100.dp)
            .offset {
                IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
            }
            .pointerInput(isDragging) {
                if (isDragging) {
                    detectDragGestures(
                        onDragEnd = {
                            val tileX =
                                ((((offsetX - middle + TILE_WIDTH) / TILE_WIDTH)
                                        + ((offsetY + TILE_HEIGHT * 3) / TILE_HEIGHT)) * 0.5f).roundToInt()
                            val tileY =
                                ((-((offsetX - middle + TILE_WIDTH) / TILE_WIDTH)
                                        + ((offsetY + TILE_HEIGHT * 3) / TILE_HEIGHT)) * 0.5f).roundToInt()

                            val range = 0 until 5

                            if (tileX in range && tileY in range && isEmptySpace(tileX,tileY)) {
                                prevX = offsetX
                                prevY = offsetY
                                onUpdateTree(tileX, tileY)
                            } else {
                                offsetX = prevX
                                offsetY = prevY
                            }
                        }
                    ) { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
            }
            .clickable {
                onSelectTree(forestTree)
            }
    ) {
        val context = LocalContext.current
        val id = getImageID(forestTree, context)

        Image(
            bitmap = ImageBitmap.imageResource(id = id),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AvailableTreesGrid(trees: List<ForestTree>, forestIndex: Int, viewModel: AppViewModel, onTreeSelected: (ForestTree) -> Unit) {
    val availableTreeIds = trees.filter { !it.onForest }
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = availableTreeIds) { tree ->
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
                    .background(Color.LightGray)
                    .clickable {
                        val availableSpot = findAvailableSpot(trees, forestIndex)
                        if (availableSpot != null) {
                            if (tree.id != -1) {
                                val updatedTree: ForestTree = tree.copy(
                                    xPosition = availableSpot.first,
                                    yPosition = availableSpot.second,
                                    onForest = true,
                                    forestId = forestIndex
                                )
                                viewModel.UpdateForestTree(updatedTree)
                                onTreeSelected(updatedTree)
                            }
                        }
                    }
            ) {
                val context = LocalContext.current
                val id = getImageID(tree, context)
                Image(
                    bitmap = ImageBitmap.imageResource(id = id), //treeID로 image
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp)
                )
            }
        }
    }
}


@Composable
fun ViewSelected(forestTree:ForestTree){
    Row {
        val context = LocalContext.current
        val id = getImageID(forestTree,context)
        Image(bitmap = ImageBitmap.imageResource(id = id), contentDescription = "",
            modifier = Modifier.size(100.dp))
        Column {
            Text(text = "${forestTree.taskDescription}")
            Text(text = "${forestTree.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}")
            Text(text = "${Duration.between(forestTree.startTime, forestTree.endTime).toMinutes()}분")
        }
    }
}

@Composable
fun ForestNavigation(forestIndex: Int, onIndexChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(onClick = { if (forestIndex > 0) onIndexChange(forestIndex - 1) },
            modifier = Modifier.weight(1f)) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_double_arrow_left_24),
                contentDescription = "previous forest"
            )
        }
        Spacer(modifier = Modifier.weight(0.5f))

        Text(text = (forestIndex + 1).toString(),
            fontSize = 30.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.weight(0.5f))

        Button(onClick = { if (forestIndex < MAX_FOREST_INDEX) onIndexChange(forestIndex + 1) },
            modifier = Modifier.weight(1f)) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_double_arrow_right_24),
                contentDescription = "next forest"
            )
        }
    }
}

fun findAvailableSpot(trees: List<ForestTree>, forestIndex: Int): Pair<Int, Int>? {
    for (y in 0..4) {
        for (x in 0..4) {
            val spotTaken = trees.any { it.forestId == forestIndex &&
                    it.xPosition == x && it.yPosition == y && it.onForest }
            if (!spotTaken) {
                return Pair(x, y)
            }
        }
    }
    return null
}

@Composable
fun IsometricTilemap(tiles: List<Tile>, middle : Float) {
    tiles.forEach { tile ->
        val imageBitmap = ImageBitmap.imageResource(id = tile.resourceId)
        IsometricTile(tile, imageBitmap,middle)
    }
}

@Composable
fun IsometricTile(tile: Tile, imageBitmap: ImageBitmap, middle : Float) {
    val xOffset = (tile.x - tile.y) * TILE_WIDTH + (middle - TILE_WIDTH)
    val yOffset = (tile.x + tile.y) * TILE_HEIGHT
    val position = IntOffset(xOffset.toInt(), yOffset)

    Image(
        bitmap = imageBitmap,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .offset { position }
            .size(80.dp)
    )
}



fun getImageID(forestTree: ForestTree, context: Context): Int {
    val treetype = when (forestTree.treeId) {
        0 -> "apple"
        1 -> "birch"
        2 -> "cedar"
        3 -> "fir"
        4 -> "maple"
        5 -> "pine"
        6 -> "spruce"
        else -> ""
    }
    val imagePath = "${treetype}_level${forestTree.treeStage}_${forestTree.color}"
    return context.resources.getIdentifier(
        imagePath,
        "drawable",
        context.packageName
    )
}


@Composable
fun FloatingButton(isDragging: Boolean, onMoveToggle: () -> Unit, onDelete: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if(!isDragging) {
            Spacer(modifier = Modifier.weight(0.8f))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    onMoveToggle()
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_circle_up_24),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.weight(0.4f))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    onDelete()
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.weight(0.8f))
        }
        else{
            Spacer(modifier = Modifier.weight(0.4f))
            Text(text = "원하는 위치로 드래그", fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(0.4f))
        }
    }
}
