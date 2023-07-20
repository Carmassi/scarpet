__config() -> {
  'stay_loaded' -> true,
};

global_cleric_lvl = 1;

global_trade = encode_nbt(
	{
		'sell' -> {'id' -> 'minecraft:enchanted_book', 'Count'->1, 'StoredEnchantments'-> ('id':'swift_sneak','lvl':'3')},
		'buy' -> {'id' -> 'minecraft:emerald', 'Count' -> 64},
		'buyB' -> {'id' -> 'minecraft:book', 'Count' -> 1},
		'uses' -> 0,
		'demand' -> -20,
		'priceMutiplier' -> 0.0,
		'specialPrice' -> 0,
		'rewardExp' -> 1, 
		'xp' -> 1,
	}
);

_add_trades(cleric, p, nbt) -> (
    if(nbt:'VillagerData':'level'>=global_cleric_lvl,

        put(nbt, 'Offers.Recipes', global_trade, -1 );
        modify(cleric, 'nbt_merge', nbt);
        modify(cleric, 'tag', 'SwiftSneakTrader:HasSwiftSneak')
    );
);

__on_player_interacts_with_entity(player, entity, hand) -> (
    if(entity~'type'=='villager',
		nbt = entity~'nbt';
		if(nbt:'VillagerData':'profession'=='minecraft:cleric' && 
			nbt:'VillagerData':'level' >= global_cleric_lvl &&
			parse_nbt(nbt:'Tags')~'SwiftSneakTrader:HasSwiftSneak' == null
			, 
			_add_trades(entity, player, nbt)
		);
	);
);
