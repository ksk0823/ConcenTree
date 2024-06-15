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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.concentree.R
import com.example.concentree.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class Tile(val x: Int, val y: Int, val resourceId: Int)
data class TreeData(val id: Int, val x: Int, val y: Int, val s: Int, val forestid : Int, var onForest:Boolean)

data class ForestData(var trees: List<TreeData>, val id: Int)

private const val TILE_WIDTH = 100
private const val TILE_HEIGHT = 60
private const val BUTTON_OFFSET_Y = -40

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ForestScreen(viewModel: AppViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getTreesInShop()
    }

    var trees by rememberSaveable {
        mutableStateOf(
            listOf(
                TreeData(0, 0, 1, 1, 0, true),
                TreeData(1, 0, 3, 2, 1, true),
                TreeData(2, 2, 2, 3, 2, true),
                TreeData(3, 1, 1, 2, 1, true),
                TreeData(4, 2, 4, 2, 2, true),
                TreeData(5, 2, 1, 3, 0, true),
                TreeData(6, 3, 3, 3, 0, true),
                TreeData(7, 2, 1, 1, 1, true),
                TreeData(8, 2, 2, 0, 1, true),
                TreeData(9, 0, 0, 1, 0, true),
                TreeData(10, 1, 1, 1, 2, true),
                TreeData(11, 0, 0, 1, -1, false),
                TreeData(12, 0, 0, 1, -1, false),
                TreeData(13, 0, 0, 1, -1, false),
                TreeData(14, 0, 0, 1, -1, false),
                TreeData(15, 0, 0, 1, -1, false),
                TreeData(16, 0, 0, 1, -1, false),
                TreeData(17, 0, 0, 1, -1, false),
                TreeData(18, 0, 0, 1, -1, false),
                TreeData(19, 0, 0, 1, -1, false),
                TreeData(20, 0, 0, 1, -1, false),
                TreeData(21, 0, 0, 1, -1, false),
                TreeData(22, 0, 0, 1, -1, false),
                TreeData(23, 0, 0, 1, -1, false),
                TreeData(24, 0, 0, 1, -1, false),
                TreeData(25, 0, 0, 1, -1, false),

                ).toMutableList()
        )
    }

    var currentTree by remember { mutableStateOf<TreeData?>(null) }
    var isDragging by remember { mutableStateOf(false) }
    var forestIndex by remember { mutableIntStateOf(0) }

    val tiles = (0..4).flatMap { y ->
        (0..4).map { x ->
            Tile(x, y, R.drawable.isometric_0000)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                    ) {
                        currentTree = null
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = { if (forestIndex > 0) forestIndex-- },
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.treetype1_level1),
                        contentDescription = "previous forest"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { if (forestIndex < 2) forestIndex++ },
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.treetype1_level2),
                        contentDescription = "next forest"
                    )
                }
            }

            Column(
                modifier = Modifier.offset(165.dp, 200.dp)
            ) {
                Box {
                    IsometricTilemap(tiles)
                }
                Box(
                    modifier = Modifier.graphicsLayer(translationY = -400f)
                ) {
                    trees.filter {
                        it.forestid == forestIndex && it.onForest
                    }
                        .sortedBy { it.x + it.y }
                        .forEach { tree ->
                            Tree(
                                treeData = tree,
                                isDragging = isDragging,
                                onLongPress = {
                                    currentTree = tree
                                    isDragging = false
                                }
                            ) { newTreeData ->
                                // Update the tree in the forest
                                trees[newTreeData.id] = newTreeData
                            }
                        }
                    currentTree?.let {
                        FloatingButton(
                            tree = it,
                            isDragging = isDragging,
                            onMoveToggle = {
                                isDragging = !isDragging
                                currentTree = currentTree?.copy()
                            },
                            onDelete = {
                                trees[it.id].onForest = false
                                currentTree = null
                            }
                        )
                    }
                }
            }

            //Consider adding button
//            var isAdding by remember { mutableStateOf(false) }
//
//            Button(
//                onClick = { isAdding = !isAdding },
//                modifier = Modifier.offset(300.dp, 450.dp)
//            ) {
//                Image(imageVector = ImageVector.vectorResource(id = R.drawable.circle_icon), contentDescription = "")
//            }

            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.offset(0.dp, 550.dp)
                    .size(400.dp, 200.dp)
            ) {
                // Filter and get tree IDs that are not in the forest
                val availableTreeIds = trees.filter { !it.onForest }.map { it.id }

                items(
                    items = availableTreeIds
                ) { treeId ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(5.dp)
                            .background(Color.Gray)
                            .clickable {
                                val availableSpot = findAvailableSpot(trees, forestIndex)

                                if (availableSpot != null) {
                                    val treeIndex = trees.indexOfFirst { it.id == treeId }
                                    if (treeIndex != -1) {
                                        val updatedTree = trees[treeIndex].copy(
                                            x = availableSpot.first,
                                            y = availableSpot.second,
                                            onForest = true,
                                            forestid = forestIndex
                                        )
                                        trees = trees.toMutableList().apply {
                                            set(treeIndex, updatedTree)
                                        }
                                    }
                                } else {
                                    // Show a message indicating no available spots
                                    scope.launch {
                                        Toast.makeText(
                                            context,
                                            "No available spot to plant the tree in the current forest.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                    ) {
                        Text(
                            text = "Tree ID: $treeId",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
fun findAvailableSpot(trees: List<TreeData>, forestIndex: Int): Pair<Int, Int>? {
    for (y in 0..4) {
        for (x in 0..4) {
            val spotTaken = trees.any { it.forestid == forestIndex && it.x == x && it.y == y && it.onForest }
            if (!spotTaken) {
                return Pair(x, y)
            }
        }
    }
    return null
}



@Composable
fun IsometricTile(tile: Tile, imageBitmap: ImageBitmap) {
    val xOffset = (tile.x - tile.y) * TILE_WIDTH
    val yOffset = (tile.x + tile.y) * TILE_HEIGHT
    val position = IntOffset(xOffset, yOffset)

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
fun IsometricTilemap(tiles: List<Tile>) {
    tiles.forEach { tile ->
        val imageBitmap = ImageBitmap.imageResource(id = tile.resourceId)
        IsometricTile(tile, imageBitmap)
    }
}

@Composable
fun Tree(
    treeData: TreeData,
    isDragging: Boolean,
    onLongPress: () -> Unit,
    onUpdateTree: (TreeData) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(((treeData.x - treeData.y) * TILE_WIDTH).toFloat()) }
    var offsetY by remember { mutableFloatStateOf(((treeData.x + treeData.y) * TILE_HEIGHT).toFloat()) }
    var prevX by remember { mutableFloatStateOf(offsetX) }
    var prevY by remember { mutableFloatStateOf(offsetY) }

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
                            val tileX = (((offsetX / TILE_WIDTH) + (offsetY / TILE_HEIGHT)) * 0.5f).roundToInt()
                            val tileY = ((-(offsetX / TILE_WIDTH) + (offsetY / TILE_HEIGHT)) * 0.5f).roundToInt()
                            val range = 0 until 5

                            if (tileX in range && tileY in range) {
                                offsetX = ((tileX - tileY) * TILE_WIDTH).toFloat()
                                offsetY = ((tileX + tileY) * TILE_HEIGHT).toFloat()
                                onUpdateTree(treeData.copy(x = tileX, y = tileY))
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
        val id = when (treeData.s) {
            1 -> R.drawable.apple_level1_0
            2 -> R.drawable.apple_level1_5
            else -> R.drawable.apple_level2_1
        }

        Image(
            bitmap = ImageBitmap.imageResource(id = id),
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
fun FloatingButton(tree: TreeData, isDragging: Boolean, onMoveToggle: () -> Unit, onDelete: () -> Unit) {

    Row(
        modifier = Modifier
            .size(200.dp)
            .offset { IntOffset(((tree.x - tree.y) * TILE_WIDTH - 150), ((tree.x + tree.y) * TILE_HEIGHT - 150)) }
    ) {
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                onMoveToggle()
            }
        ) {
            val id = if (!isDragging) R.drawable.baseline_arrow_circle_up_24 else R.drawable.baseline_arrow_circle_down_24
            Image(
                imageVector = ImageVector.vectorResource(id = id),
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
    }
}
