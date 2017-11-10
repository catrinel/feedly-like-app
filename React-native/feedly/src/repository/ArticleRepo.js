
let articlesList = [
    {
        title: "Amazing news",
        description: "React app is working!",
        author: "Me",
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
        title: "Test topic",
        description: "This is a description",
        author: "Bogdi",
        topic: "Other"
    }
];

export function getArticlesList() {
    return articlesList;
}

export function getElementByTitle(title) {
    return articlesList.find(a => a.title === title);
}

export function editArticle(oldTitle, title, description, topic){
    let article = getElementByTitle(oldTitle);
    article.title = title;
    article.description = description;
    article.topic = topic;
}

export function addArticle(newTitle, newAuthor, newDescription, newTopic){
    const article = {
        title: newTitle,
        author: newAuthor,
        description: newDescription,
        topic: newTopic
    }
    console.log(articlesList.length);
    articlesList.push(article);
    console.log(article);
    console.log(articlesList.length);
}