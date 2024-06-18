package com.example.concentree.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concentree.R
import com.example.concentree.roomDB.ForestTree
import com.example.concentree.roomDB.Tree
import com.example.concentree.viewmodel.AppViewModel
import com.example.concentree.viewmodel.ForestTreeRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.math.roundToInt

data class Tile(val x: Int, val y: Int, val resourceId: Int)

private const val TILE_WIDTH = 100
private const val TILE_HEIGHT = 60
private const val MAX_FOREST_INDEX = 9
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ForestScreen(viewModel: AppViewModel) {

    val trees by viewModel.forestTreeList.collectAsState(initial = emptyList())

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

    // Fetch initial data
    LaunchedEffect(Unit) {
        viewModel.getAllTreesInForest()
        viewModel.getUserById(1)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { selectedTree = null; isDragging = false } }
    ) {
       /*
        Button(onClick = {
            viewModel.insertForestTree(
                ForestTree(
                    treeId = 6,
                    startTime = LocalDateTime.now().minusHours(2),
                    endTime = LocalDateTime.now(),
                    treeStage = 2,
                    onForest = false,
                    taskDescription = "Test Tree",
                    xPosition = 2,
                    yPosition = 2,
                    forestId = 1,
                    color = 6
                )
            )
            viewModel.insertForestTree(
                ForestTree(
                    treeId = 1,
                    startTime = LocalDateTime.now().minusHours(2),
                    endTime = LocalDateTime.now(),
                    treeStage = 3,
                    onForest = false,
                    taskDescription = "Test Tree",
                    xPosition = 2,
                    yPosition = 3 ,
                    forestId = 1,
                    color = 1
                )
            )

            viewModel.insertForestTree(
                ForestTree(
                    treeId = 6,
                    startTime = LocalDateTime.now().minusHours(2),
                    endTime = LocalDateTime.now(),
                    treeStage = 2,
                    onForest = false,
                    taskDescription = "Test Tree",
                    xPosition = 4,
                    yPosition = 1 ,
                    forestId = 0,
                    color =2
                )
            )

            viewModel.insertForestTree(
                ForestTree(
                    treeId = 4,
                    startTime = LocalDateTime.now().minusHours(2),
                    endTime = LocalDateTime.now(),
                    treeStage = 1,
                    onForest = false,
                    taskDescription = "Test Tree",
                    xPosition = 3,
                    yPosition = 2 ,
                    forestId = 1,
                    color = 4
                )
            )
        }) {}
       */ //insert data for test

        Column {
            Spacer(modifier = Modifier.height(20.dp))

            ForestNavigation(forestIndex = forestIndex, onIndexChange = { forestIndex = it })

            Spacer(modifier = Modifier.height(100.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeightPx / 8).dp)
            ) {
                IsometricTilemap(tiles, screenWidthPx / 2)

                trees.filter { it.forestId == forestIndex && it.onForest }
                    .sortedBy { it.xPosition + it.yPosition }
                    .forEach { tree ->
                        Plant(
                            forestTree = tree,
                            isDragging = (selectedTree?.id == tree.id) && isDragging,
                            middle = screenWidthPx / 2,
                            onLongPress = {
                                selectedTree = tree
                                isDragging = false
                            },
                            onUpdateTree = { newTreeData ->
                                viewModel.UpdateForestTree(newTreeData)
                                selectedTree = null
                            }
                        )
                    }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            {
                selectedTree?.let {
                    FloatingButton(
                        isDragging = isDragging,
                        onMoveToggle = {
                            isDragging = !isDragging
                            selectedTree = selectedTree?.copy()
                        },
                        onDelete = {
                            val updateTree = selectedTree!!.copy(onForest = false)
                            viewModel.UpdateForestTree(updateTree)
                            selectedTree = null
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AvailableTreesGrid(trees = trees, forestIndex = forestIndex, viewModel = viewModel)
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
        Button(onClick = { if (forestIndex > 0) onIndexChange(forestIndex - 1) }, modifier = Modifier.weight(1f)) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_double_arrow_left_24),
                contentDescription = "previous forest"
            )
        }
        Spacer(modifier = Modifier.weight(0.5f))
        Text(text = (forestIndex+1).toString(), fontSize = 30.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(0.5f))
        Button(onClick = { if (forestIndex < MAX_FOREST_INDEX) onIndexChange(forestIndex + 1) }, modifier = Modifier.weight(1f)) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_keyboard_double_arrow_right_24),
                contentDescription = "next forest"
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AvailableTreesGrid(trees: List<ForestTree>, forestIndex: Int, viewModel: AppViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val availableTreeIds = trees.filter { !it.onForest }.map { it.id }
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = availableTreeIds) { treeId ->
            val treeIndex = trees.indexOfFirst { it.id == treeId }
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(5.dp)
                    .background(Color.LightGray)
                    .clickable {
                        val availableSpot = findAvailableSpot(trees, forestIndex)
                        if (availableSpot != null) {
                            if (treeIndex != -1) {
                                val updatedTree: ForestTree = trees[treeIndex].copy(
                                    xPosition = availableSpot.first,
                                    yPosition = availableSpot.second,
                                    onForest = true,
                                    forestId = forestIndex
                                )
                                viewModel.UpdateForestTree(updatedTree)
                            }
                        } else {
                            scope.launch {
                                Toast
                                    .makeText(
                                        context,
                                        "No available spot to plant the tree in the current forest.",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                    }
            ) {
                val treetype = when (trees[treeIndex].treeId) {
                    0 -> "apple"
                    1 -> "birch"
                    2 -> "cedar"
                    3 -> "fir"
                    4 -> "maple"
                    5 -> "pine"
                    6 -> "spruce"
                    else -> {}
                }
                val imagepath = "${treetype}_level${trees[treeIndex].treeStage}_${trees[treeIndex].color}"
                val context = LocalContext.current
                val id = context.resources.getIdentifier(
                    imagepath,
                    "drawable",
                    context.packageName
                )
                Image(
                    bitmap = ImageBitmap.imageResource(id =id), //treeID로 image
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp)
                )
            }
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

@Composable
fun IsometricTilemap(tiles: List<Tile>, middle : Float) {
    tiles.forEach { tile ->
        val imageBitmap = ImageBitmap.imageResource(id = tile.resourceId)
        IsometricTile(tile, imageBitmap,middle)
    }
}

@Composable
fun Plant(
    forestTree: ForestTree,
    isDragging: Boolean,
    middle: Float,
    onLongPress: () -> Unit,
    onUpdateTree: (ForestTree) -> Unit
) {
    val initialOffsetX = remember(forestTree.xPosition, forestTree.yPosition)
    { ((forestTree.xPosition - forestTree.yPosition) * TILE_WIDTH) + (middle - TILE_WIDTH) }

    val initialOffsetY = remember(forestTree.xPosition, forestTree.yPosition)
    { (((forestTree.xPosition + forestTree.yPosition) * TILE_HEIGHT) - TILE_HEIGHT * 3).toFloat() }

    var offsetX by remember { mutableFloatStateOf(initialOffsetX) }
    var offsetY by remember { mutableFloatStateOf(initialOffsetY) }
    var prevX by remember { mutableFloatStateOf(offsetX) }
    var prevY by remember { mutableFloatStateOf(offsetY) }

    LaunchedEffect(forestTree.xPosition, forestTree.yPosition) {
        offsetX = ((forestTree.xPosition - forestTree.yPosition) * TILE_WIDTH) + (middle - TILE_WIDTH)
        offsetY = ((forestTree.xPosition + forestTree.yPosition) * TILE_HEIGHT - TILE_HEIGHT * 3).toFloat()
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
                        onDragStart = {
                            prevX = offsetX
                            prevY = offsetY
                        },
                        onDragEnd = {
                            val tileX =
                                ((((offsetX - middle + TILE_WIDTH) / TILE_WIDTH)
                                        + ((offsetY + TILE_HEIGHT * 3) / TILE_HEIGHT)) * 0.5f).roundToInt()
                            val tileY =
                                ((-((offsetX - middle + TILE_WIDTH) / TILE_WIDTH)
                                        + ((offsetY + TILE_HEIGHT * 3) / TILE_HEIGHT)) * 0.5f).roundToInt()
                            val range = 0 until 5

                            if (tileX in range && tileY in range) {
                                offsetX =
                                    ((forestTree.xPosition - forestTree.yPosition) * TILE_WIDTH) + (middle - TILE_WIDTH)
                                offsetY =
                                    ((forestTree.xPosition + forestTree.yPosition) * TILE_HEIGHT - TILE_HEIGHT * 3).toFloat()
                                offsetX = prevX
                                offsetY = prevY

                                onUpdateTree(forestTree.copy(xPosition = tileX, yPosition = tileY))
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
    ) {
        val treetype = when (forestTree.treeId) {
            0 -> "apple"
            1 -> "birch"
            2 -> "cedar"
            3 -> "fir"
            4 -> "maple"
            5 -> "pine"
            6 -> "spruce"
            else -> {}
        }
        val imagepath = "${treetype}_level${forestTree.treeStage}_${forestTree.color}"
        val context = LocalContext.current
        val id = context.resources.getIdentifier(
            imagepath,
            "drawable",
            context.packageName
        )
        Image(
            bitmap = ImageBitmap.imageResource(id =id), //treeID로 image
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress()
                        }
                    )
                }
        )
    }
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
