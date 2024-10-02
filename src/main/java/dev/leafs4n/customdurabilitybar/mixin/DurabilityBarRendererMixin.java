package dev.leafs4n.customdurabilitybar.mixin;

import dev.leafs4n.customdurabilitybar.CustomDurabilityBar;
import dev.leafs4n.customdurabilitybar.config.DurabilityBarConfigModel;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DrawContext.class)
public abstract class DurabilityBarRendererMixin {

	@Shadow
	@Final
	private MatrixStack matrices;

	@Shadow
	@Final
	private MinecraftClient client;

	// Textures
	private final Identifier[] DURABILITY_BAR_TEXTURE = new Identifier[]{
			Identifier.of("customdurabilitybar", "textures/gui/leaves_background_16x16.png"),
			Identifier.of("customdurabilitybar", "textures/gui/leaves_colourful_background_16x16.png")
	};

	/**
	 * @author leafs4n
	 * @reason
	 */
	@Overwrite
	public void drawItemInSlot(TextRenderer textRenderer, ItemStack stack, int x, int y, @Nullable String countOverride){
		ClientPlayerEntity clientPlayerEntity;
		float f;
		int l;
		int k;

		int Xdisplacement = CustomDurabilityBar.CONFIG.xPosition();
		int Ydisplacement = CustomDurabilityBar.CONFIG.yPosition()*-1;
		if (stack.isEmpty()) {
			return;
		}
		this.matrices.push();
		if (stack.getCount() != 1 || countOverride != null) {
			String string = countOverride == null ? String.valueOf(stack.getCount()) : countOverride;
			this.matrices.translate(0.0f, 0.0f, 200.0f);
			this.drawText(textRenderer, string, x + 19 - 2 - textRenderer.getWidth(string), y + 6 + 3, 0xFFFFFF, true);
		}
		// Bar rendering
		if (stack.isItemBarVisible()) {

			if (CustomDurabilityBar.CONFIG.durabilityBarType().equals(DurabilityBarConfigModel.DurabilityBarType.DEFAULT)) {
				// Defaul bar
				int i = stack.getItemBarStep();
				int j = stack.getItemBarColor();

				if(CustomDurabilityBar.CONFIG.durabilityBarOrientation().equals(DurabilityBarConfigModel.DurabilityBarOrientation.HORIZONTAL)) {
					k = x + 2 + Xdisplacement;
					l = y + 13 + Ydisplacement;
					this.fill(RenderLayer.getGuiOverlay(), k, l, k + 13, l + 2, -16777216);
					this.fill(RenderLayer.getGuiOverlay(), k, l, k + i, l + 1, j | 0xFF000000);
				}else{
					k = x + 13 + Xdisplacement;
					l = y + 2 + Ydisplacement;
					this.fill(RenderLayer.getGuiOverlay(), k, l, k + 2, l + 13, -16777216);
					this.fill(RenderLayer.getGuiOverlay(), k, l+13-i, k + 1, l + 13, j | 0xFF000000);
				}
			}else if(CustomDurabilityBar.CONFIG.durabilityBarType().equals(DurabilityBarConfigModel.DurabilityBarType.HIDE)){
				// Hidden bar
			}else{
				int orientationSwitch = (CustomDurabilityBar.CONFIG.durabilityBarOrientation().equals(DurabilityBarConfigModel.DurabilityBarOrientation.HORIZONTAL)) ? 0 : 16;
				this.drawTexture(DURABILITY_BAR_TEXTURE[CustomDurabilityBar.CONFIG.durabilityBarType().ordinal()], x+Xdisplacement, x+16+Xdisplacement, y+Ydisplacement, y+16+Ydisplacement,200, 16, 16, 16*(13-stack.getItemBarStep()), orientationSwitch, 224,32);
			}
		}

		float f2 = f = (clientPlayerEntity = this.client.player) == null ? 0.0f : clientPlayerEntity.getItemCooldownManager().getCooldownProgress(stack.getItem(), this.client.getRenderTickCounter().getTickDelta(true));
		if (f > 0.0f) {
			k = y + MathHelper.floor(16.0f * (1.0f - f));
			l = k + MathHelper.ceil(16.0f * f);
			this.fill(RenderLayer.getGuiOverlay(), x, k, x + 16, l, Integer.MAX_VALUE);
		}
		this.matrices.pop();
	}

	@Shadow
	protected abstract void fill(RenderLayer layer, int x1, int y1, int x2, int y2, int color);

	@Shadow
	protected abstract int drawText(TextRenderer textRenderer, @Nullable String text, int x, int y, int color, boolean shadow);

	@Shadow
	protected abstract void drawTexture(Identifier texture, int x1, int x2, int y1, int y2, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight);

}