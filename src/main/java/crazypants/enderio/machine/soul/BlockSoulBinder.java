package crazypants.enderio.machine.soul;

import java.util.Random;

import crazypants.enderio.GuiHandler;
import crazypants.enderio.ModObject;
import crazypants.enderio.machine.AbstractMachineBlock;
import crazypants.enderio.xp.PacketDrainPlayerXP;
import crazypants.enderio.xp.PacketExperianceContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockSoulBinder extends AbstractMachineBlock<TileSoulBinder> {
  
  public static int renderId;
  
  public static BlockSoulBinder create() {
    PacketDrainPlayerXP.register();
    PacketExperianceContainer.register();
    BlockSoulBinder result = new BlockSoulBinder();
    result.init();
    return result;
  }
  
  protected BlockSoulBinder() {
    super(ModObject.blockSoulBinder, TileSoulBinder.class);
  }  
  
  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
    if(te instanceof TileSoulBinder) {
      return new ContainerSoulBinder(player.inventory, (TileSoulBinder) te);
    }
    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
    if(te instanceof TileSoulBinder) {
      return new GuiSoulBinder(player.inventory, (TileSoulBinder) te);
    }
    return null;
  }

  @Override
  protected int getGuiId() {
    return GuiHandler.GUI_ID_SOUL_BINDER;
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }
  
  @Override
  public int getLightOpacity() {
    return 0;
  }
  
//  @Override
//  @SideOnly(Side.CLIENT)
//  public void registerBlockIcons(IIconRegister iIconRegister) {    
//    super.registerBlockIcons(iIconRegister);
  // TODO zombieSkullIcon = iIconRegister.registerIcon("enderio:skullZombie");
//    creeperSkullIcon = iIconRegister.registerIcon("enderio:skullCreeper");
//    skeletonSkullIcon = iIconRegister.registerIcon("enderio:skullSkeleton");
//    endermanSkullIcon = iIconRegister.registerIcon("enderio:endermanSkullFront");
//    endermanSkullIconOn= iIconRegister.registerIcon("enderio:endermanSkullFrontEyes");
//  }

  @Override
  public int getRenderType() {    
    return renderId;
  }
  
  
  
  @Override
  public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
    int x = pos.getX();
    int y = pos.getY();
    int z = pos.getZ();
     // If active, randomly throw some smoke around
    if(isActive(world, x, y, z)) {
      float startX = x + 1.0F;
      float startY = y + 1.0F;
      float startZ = z + 1.0F;
      for (int i = 0; i < 2; i++) {
        float xOffset = -0.2F - rand.nextFloat() * 0.6F;
        float yOffset = -0.1F + rand.nextFloat() * 0.2F;
        float zOffset = -0.2F - rand.nextFloat() * 0.6F;        
        
        EntityFX fx = Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(EnumParticleTypes.SPELL.getParticleID(), startX + xOffset, startY + yOffset, startZ + zOffset, 0.0D, 0.0D, 0.0D, 0);
        if(fx != null) {
          fx.setRBGColorF(0.2f, 0.2f, 0.8f);          
          //fx.setRBGColorF(0.1f, 0.4f, 0.1f);
          fx.motionY *= 0.5f;
        }

      }
    }
  }  

}
