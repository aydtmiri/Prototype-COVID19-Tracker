// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access Cloud Firestore.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


exports.sendNotificationsToToken = functions.https.onCall(async(data) => {
  const token = data.text;

  if (token === null)
    throw new functions.https.HttpsError('failes_notification', 'The token is null');
  else

    console.log('User to send notification', token);

  const payload = {
    notification: {
      title: 'Neue COVID-19 Meldung',
      body: 'Es gab eine neue COVID-19 Meldung in Ihrer Nähe. Bitte öffnen Sie die App für mehr Informationen.'
    }

  };

  admin.messaging().sendToDevice(token, payload)
  .then((response)=> {
        return console.log("Successfully sent message:", response);
        })
        .catch((error) =>{
        console.log("Error sending message:", error);
        });
});
