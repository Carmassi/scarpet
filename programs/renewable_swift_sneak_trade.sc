global_trade = encode_nbt(
	{
		'sell' -> {'id' -> 'minecraft:enchanted_book', 'Count'-> 1, 
			'tag' -> {'StoredEnchantments' -> [{
				'id' -> 'minecraft:swift_sneak', 'lvl' -> 3}]}
		},
		'buy' -> {'id' -> 'minecraft:emerald', 'Count' -> 64},
		'buyB' -> {'id' -> 'minecraft:sculk', 'Count' -> 64},
		'uses' -> 0,
		'demand' -> -20,
		'priceMutiplier' -> 0.0,
		'specialPrice' -> 0,
		'rewardExp' -> 1,
		'xp' -> 1, 
	}
);

_add_trade(e, p, nbt) -> (
	if(
		p~'holds':0 == 'sculk' && //is player holding sculk?
		inventory_remove(p, 'sculk', 64) //can 64 sculk be removed?
		,
		run(str('/data modify entity %s Offers.Recipes append value %s', e~'command_name', global_trade)); //adds global_trade
		sound('entity.warden.sonic_boom', e~'pos');
		modify(e, 'tag', 'HasSwiftSneak'); //Give tag to mark the shepherd
		);
);

__on_player_interacts_with_entity(player, entity, hand) -> (
	if(entity~'type'=='villager',
		nbt = entity~'nbt';
		if(
			nbt:'VillagerData':'profession'=='minecraft:shepherd' && //is shepherd?
			parse_nbt(nbt:'Tags')~'HasSwiftSneak' == null //it doesn't have the tag?
			,
			_add_trade(entity, player, nbt)
		);
	)
);
