package dungeonmania.response.models;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleRound;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.LogicExtension.LightBulb;
import dungeonmania.util.NameConverter;
import java.util.stream.Collectors;

/**
 * You may change this file at your own discretion.
 * However, you must always make sure your changes pass MVP tests and the dryrun.
 */
public class ResponseBuilder {
    public static DungeonResponse getDungeonResponse(Game game) {
        List<EntityResponse> entityResponse = game.getAllEntities().stream().map(e -> getEntityResponse(game, e))
                .collect(Collectors.toList());

        List<ItemResponse> inventoryResponse = game.getPlayerInventoryResponses();

        return new DungeonResponse(game.getId(), game.getName(), entityResponse, inventoryResponse,
                game.getBattleFacade().getBattleResponses(),
                (game.getPlayer() != null) ? game.getAvailableBuildables() : null,
                (game.getGoals().achieved(game)) ? "" : game.getGoals().toString(game));
    }

    public static ItemResponse getItemResponse(Entity entity) {
        return new ItemResponse(entity.getId(), NameConverter.toSnakeCase(entity));
    }

    public static String wrapEntityType(Entity entity) {
        String s = NameConverter.toSnakeCase(entity);
        if (entity instanceof LightBulb l) {
            s += l.isOn() ? "_on" : "_off";
        }
        return s;
    }

    public static EntityResponse getEntityResponse(Game game, Entity entity) {
        return new EntityResponse(entity.getId(), wrapEntityType(entity), entity.getPosition(),
                (entity instanceof Interactable interactable) && interactable.isInteractable(game.getPlayer()));
    }

    public static RoundResponse getRoundResponse(BattleRound round) {
        return new RoundResponse(round.getDeltaSelfHealth(), round.getDeltaTargetHealth());
    }
}
