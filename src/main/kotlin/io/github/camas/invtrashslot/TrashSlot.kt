package io.github.camas.invtrashslot

import net.minecraft.client.MinecraftClient
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.slot.Slot

const val TAG_KEY = "inv-trash-slot"
const val INVENTORY_TYPE = 10

/**
 * Main trash slot logic
 */
class PlayerTrashSlot {
    var inventory: TrashSlotInventory = TrashSlotInventory()

    /**
     * Reads slot data from a [NbtCompound]
     */
    fun readFromTag(tag: NbtCompound) {
        inventory.readNbtList(tag.getList(TAG_KEY, INVENTORY_TYPE))
    }

    /**
     * Writes slot data to a a [NbtCompound]
     */
    fun writeToTag(tag: NbtCompound) {
        tag.put(TAG_KEY, inventory.toNbtList())
    }
}

/**
 * A single slot inventory that can always be inserted into
 */
class TrashSlotInventory : SimpleInventory(1) {
    override fun canInsert(stack: ItemStack?): Boolean {
        return true
    }
}

class SurvivalOnlySlot(inventory: Inventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {
    override fun isEnabled(): Boolean {
        return !MinecraftClient.getInstance().interactionManager!!.hasCreativeInventory();
    }
}

/**
 * Interface solely used to add a PlayerTrashSlot to PlayerEntity
 */
interface ITrashSlot {
    fun getTrashSlot(): PlayerTrashSlot
}
