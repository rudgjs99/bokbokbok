package net.rudgjs99.bokbokbok;

import com.draylar.identity.api.Components;
import com.draylar.identity.api.PlayerIdentity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class Bokbokbok implements ClientModInitializer {

    // 'G' 키를 "Ability"라는 이름의 키 바인딩으로 등록합니다.
    private static final KeyBinding abilityKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    "key.bokbokbok.ability", // lang 파일에 정의될 번역 키
                    InputUtil.Type.KEYSYM, // 키보드 입력
                    GLFW.GLFW_KEY_G,       // 기본 키는 'G'
                    "category.bokbokbok.main" // 키 설정 카테고리
            )
    );

    @Override
    public void onInitializeClient() {
        // 클라이언트 틱 이벤트 리스너를 등록합니다.
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // 키가 눌렸는지 확인합니다. client.player가 null이 아니어야 합니다.
            while (abilityKey.wasPressed() && client.player != null) {
                // 플레이어의 현재 변신 정보를 가져옵니다.
                EntityType<?> identityType = PlayerIdentity.getIdentity(client.player).getType();

                // 변신 상태가 아니면(플레이어 자신이면) 아무것도 하지 않습니다.
                if (identityType == EntityType.PLAYER) {
                    client.player.sendMessage(Text.literal("§c변신 상태가 아닙니다."), false);
                    return;
                }

                // 변신한 몹의 ID를 가져옵니다.
                Identifier mobId = EntityType.getId(identityType);

                // 채팅창에 현재 몹 ID를 출력합니다.
                client.player.sendMessage(Text.literal("§a[BokBokBok] §fCurrent Identity: §e" + mobId.toString()), false);
            }
        });
    }
}
