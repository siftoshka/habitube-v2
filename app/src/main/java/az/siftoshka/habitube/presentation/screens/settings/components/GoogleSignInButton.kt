package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.theme.spacing
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoogleSignInButton() {
    val isNotLogIn = remember { mutableStateOf(FirebaseAuth.getInstance().currentUser == null) }
    val indicator = if (isNotLogIn.value) rememberRipple(color = MaterialTheme.colors.secondaryVariant) else null
    val borderColor = if (isNotLogIn.value) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.primary
    val context = LocalContext.current
    val token = stringResource(R.string.default_web_client_id)

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { activityResult ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            Firebase.auth.signInWithCredential(credential).addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {
                    isNotLogIn.value = false
                }
            }
        } catch (e: ApiException) {
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.extraSmall)
            .border(width = 1.dp, color = borderColor, shape = MaterialTheme.shapes.large),
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        indication = indicator,
        onClick = {
            if (isNotLogIn.value) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token).requestEmail().build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }
        }
    ) {
        if (isNotLogIn.value) {
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    modifier = Modifier.size(20.dp),
                    contentDescription = stringResource(id = R.string.text_sign_in),
                )
                Text(
                    text = stringResource(id = R.string.text_sign_in),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                )
            }
        } else if (!isNotLogIn.value) {
            AccountView(isNotLogIn, user = FirebaseAuth.getInstance().currentUser)
        }
    }
}

@Composable
fun AccountView(
    isNotLogIn: MutableState<Boolean>,
    user: FirebaseUser?
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default, vertical = MaterialTheme.spacing.small)
    ) {
        FirebaseAvatar(imageUrl = user?.photoUrl.toString(), title = user?.displayName)
        Column(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)
        ) {
            Text(
                text = user?.displayName.orEmpty(),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = user?.email.orEmpty(),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = {
            FirebaseAuth.getInstance().signOut()
            GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut().addOnCompleteListener {
                isNotLogIn.value = true
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                modifier = Modifier.size(20.dp),
                contentDescription = stringResource(id = R.string.text_log_out),
            )
        }
    }
}