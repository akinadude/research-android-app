package ru.akinadude.research.utils

import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumes
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.selects.whileSelect
import kotlin.coroutines.CoroutineContext

/*fun <T, R> ReceiveChannel<T>.switchMap(
        scope: CoroutineScope,
        context: CoroutineContext = Unconfined,
        transform: suspend (T) -> ReceiveChannel<R>
): ReceiveChannel<R> {
    val input = this

    @UseExperimental(kotlinx.coroutines.InternalCoroutinesApi::class)
    return scope.produce(context, onCompletion = consumes()) {
        var current: ReceiveChannel<R> = transform(input.receive())
        val output = this

        whileSelect {
            input.onReceiveOrNull { t: T? ->
                t?.also { current = transform(it) } != null
            }

            current.onReceiveOrNull { r ->
                r?.also { output.send(it) } != null
            }
        }
    }
}*/

fun <T, R> CoroutineScope.switchMap(
        input: ReceiveChannel<T>,
        transform: suspend (T) -> ReceiveChannel<R>
): ReceiveChannel<R> = produce {
    var current: ReceiveChannel<R> = transform(input.receive())
    val output = this

    whileSelect {
        input.onReceiveOrNull { t: T? ->
            t?.also { current = transform(it) } != null
        }

        current.onReceiveOrNull { r ->
            r?.also { output.send(it) } != null
        }
    }
}

fun <T, R> CoroutineScope.switchMap2(
        input: ReceiveChannel<T>,
        transform: suspend (T) -> Deferred<R>
): ReceiveChannel<R> = produce {
    var current= transform(input.receive())
    Logger.d("OLOLO, transform performed, result: ${current}")
    val output = this

    whileSelect {
        input.onReceiveOrNull { t: T? ->
            val temp = t?.also { current = transform(it) }
            Logger.d("OLOLO, input.onReceiveOrNull result: ${temp != null}")
            temp != null
        }

        current.onAwait { r ->
            val temp2 = r?.also { output.send(it) }
            Logger.d("OLOLO, current.onAwait result: ${temp2 != null}")
            temp2 == null
        }
    }

    /*var current = transform(input.receive())
    while (isActive) {
        val next = select<Deferred<String>?> { // return next deferred value from this select or null
            input.onReceiveOrNull { update ->
                update // replaces next value to wait
            }
            current.onAwait { value ->
                send(value) // send value that current deferred has produced
                input.receiveOrNull() // and use the next deferred from the input channel
            }
        }
        if (next == null) {
            println("Channel was closed")
            break // out of loop
        } else {
            current = next
        }
    }*/
    //switchMap2 проблемы с постоянным вызовом current.onAwait {...}
    //switchMap проблемы с тем, что не вызывается produce<UsersContainer> { manager.searchUsersCo(searchText) } в цепочке
    //Нужно разобраться, как все это работает
}
