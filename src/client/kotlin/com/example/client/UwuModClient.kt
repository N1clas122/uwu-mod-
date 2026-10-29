package com.example.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
import net.minecraft.client.Minecraft
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.tags.BlockTags
import kotlin.random.Random

/** UwU-Mod: UwUifiziert ausgehende Chat-Nachrichten und laesst Ambosse Herzen verlieren. */
object UwuModClient : ClientModInitializer {
	private const val RADIUS = 12
	private val faces = listOf(" UwU", " OwO", " >w<", " ^w^", " :3")

	override fun onInitializeClient() {
		ClientSendMessageEvents.MODIFY_CHAT.register { msg -> uwuify(msg) }
		ClientTickEvents.END_CLIENT_TICK.register { mc -> tick(mc) }
	}

	private fun tick(mc: Minecraft) {
		val player = mc.player ?: return
		val level = mc.level ?: return
		if (player.tickCount % 10 != 0) return
		val p = player.blockPosition()
		for (pos in net.minecraft.core.BlockPos.betweenClosed(
			p.offset(-RADIUS, -4, -RADIUS), p.offset(RADIUS, 4, RADIUS)
		)) {
			if (level.getBlockState(pos).`is`(BlockTags.ANVIL) && Random.nextInt(3) == 0) {
				level.addParticle(
					ParticleTypes.HEART,
					pos.x + 0.2 + Random.nextDouble() * 0.6,
					pos.y + 1.0 + Random.nextDouble() * 0.3,
					pos.z + 0.2 + Random.nextDouble() * 0.6,
					0.0, 0.02, 0.0
				)
			}
		}
	}

	private fun uwuify(s: String): String {
		if (s.startsWith("/")) return s
		val r = s.replace(Regex("[rl]"), "w").replace(Regex("[RL]"), "W")
			.replace("na", "nya").replace("ne", "nye").replace("ni", "nyi")
			.replace("no", "nyo").replace("nu", "nyu")
		return r + faces.random()
	}
}
