package org.aleksander.sportradar.codingtest.objects;

public record Team(String teamName) {

    @Override
    public String toString() {
        return this.teamName;
    }
}
