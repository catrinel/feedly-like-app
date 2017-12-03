import { AsyncStorage } from 'react-native';

let articlesListBoot = [
    {
        title: "Amazing news",
        description: "React app is working!",
        author: "Catri",
        topic: "React"
    },
    {
        title: "Lol season coming up",
        description: "New season starts November 8!",
        author: "Riot",
        topic: "Gaming"
    },
    {
        title: "No pay November",
        description: "PS is giving free games in November!",
        author: "PS Blog",
        topic: "Gaming"
    },
    {
        title: "Overwhelming amount of lab projects",
        description: "This is a description",
        author: "Bogdi",
        topic: "Other"
    }
];

export function getArticlesList() {
    return articlesListBoot;
}

export async function editArticle(oldTitle, title, description, topic, author){
    let response = await AsyncStorage.getItem('articleList'); 
    let articleList = await JSON.parse(response);

    let article = articleList.find(a => a.title === oldTitle);

    article.title = title;
    article.description = description;
    article.topic = topic;
    article.author = author;

    await AsyncStorage.setItem('articleList', JSON.stringify(articleList));
}

export async function addArticle(newTitle, newAuthor, newDescription, newTopic){
    const article = {
        title: newTitle,
        author: newAuthor,
        description: newDescription,
        topic: newTopic
    }
    let response = await AsyncStorage.getItem('articleList'); 
    let articleList = await JSON.parse(response);

    articleList.push(article);

    await AsyncStorage.setItem('articleList', JSON.stringify(articleList));
}

export async function deleteArticle(title){
    let response = await AsyncStorage.getItem('articleList'); 
    let articleList = await JSON.parse(response);

    let article = articleList.find(a => a.title === title);
    let index = articleList.indexOf(article);

    if (index > -1) {
        articleList.splice(index, 1);
    }

    await AsyncStorage.setItem('articleList', JSON.stringify(articleList));
}