package com.discordteams.feature;
import com.discordteams.command.Notice;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;

import net.dv8tion.jda.internal.utils.Checks;

import java.util.List;


// 곧 버려질 Class
public class Team extends BasicFeature {

    public Team(JDA jda, Guild guild, User user, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        super(jda, guild, user, textChannel, message, mongoClient, mongoDatabase);
        this.commandSelector();
    }

    public Role updateRole(Member member) {
        Checks.notNull(member, "Member object can not be null");

        List<Role> roles = member.getRoles();
        if (roles.isEmpty()) {
            return null;
        }

        return roles.stream().min((first, second) -> {
            if (first.getPosition() == second.getPosition()) {
                return 0;
            }
            return first.getPosition() > second.getPosition() ? -1 : 1;
        }).get();
    }

    public void updateMember(GenericGuildEvent event) {
        System.out.println("Getting users....");
        List<User> users = jda.getUsers();
        SnowflakeCacheView<Guild> guildIds = jda.getGuildCache();
        Guild guild = event.getGuild();

        for (User user : users) {
            System.out.println(users.size());
            System.out.println(user.isBot());
        }

        System.out.println(guild);
    }

    @Override
    public void commandSelector() {
        String[] args = message.getContentRaw().substring(1).split(" ");
        switch (args[1]) {
            case "member":
                textChannel.sendMessage("member").queue();
                break;
            case "":
                textChannel.sendMessage("error").queue();
                break;
            default:
                textChannel.sendMessage("There are no command. Text !help to get a usable command :) ").queue();
                break;
        }

    }
}
