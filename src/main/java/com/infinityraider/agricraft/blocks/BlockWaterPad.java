package com.infinityraider.agricraft.blocks;

import com.infinityraider.agricraft.api.v1.IIconRegistrar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWaterPad extends AbstractBlockWaterPad {
	
	public BlockWaterPad() {
        this(Material.ground);
    }

    protected BlockWaterPad(Material mat) {
        super(mat, "normal");
    }

    @Override
    protected Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockWaterPad.class;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (stack == null || stack.getItem() == null) {
            return false;
        }
        if (FluidContainerRegistry.isContainer(stack)) {
            FluidStack waterBucket = new FluidStack(FluidRegistry.WATER, 1000);
            if (!FluidContainerRegistry.containsFluid(stack, waterBucket)) {
                return false;
            }
            if (!player.capabilities.isCreativeMode) {
                player.inventory.addItemStackToInventory(FluidContainerRegistry.drainFluidContainer(stack));
                stack.stackSize = stack.stackSize - 1;
            }
            if (!world.isRemote) {
                world.setBlockState(pos, this.getDefaultState(), 3);
            }
            return true;

        }
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float f, int i) {
        if(!world.isRemote) {
            ItemStack drop = new ItemStack(Blocks.dirt, 1);
            spawnAsEntity(world, pos, drop);
        }
    }

    //creative item picking
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Blocks.dirt);
    }

    //render methods
    //--------------
    @Override
    public boolean isOpaqueCube(IBlockState state) {return false;}

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {return true;}

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegistrar iconRegistrar) {}
}
