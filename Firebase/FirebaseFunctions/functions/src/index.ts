import * as functions from 'firebase-functions';

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const helloWorld = functions.https.onRequest((request, response) => {
    console.log('Hello!')
    response.send("This is your caption");
});

export const GetCaption = functions.https.onRequest((request, response) => {
    console.log('Hello!')
    response.send("This is your caption");
});
