package org.nmx.codingame.analyser;

import java.util.Objects;

public record PlayerInfo(String nickname, long userId, String playerAgentId) {
    @Override
    public String toString() {
        return nickname;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayerInfo other = (PlayerInfo) obj;
        return Objects.equals(nickname, other.nickname);
    }


}
